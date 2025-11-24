package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.*;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityValidationResult;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CreditApplication extends AuditableAbstractAggregateRoot<CreditApplication> {
    @NotNull
    @Embedded
    private RealStateCompanyId realStateCompanyId;

    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "housing_id", nullable = false)
    private Housing housing;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "finance_entity_id", nullable = false)
    private FinanceEntity financeEntity;

    @Embedded
    private FinanceEntityValidationResult financeEntityApproved;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "interest_rate_id", nullable = false, unique = true)
    private InterestRate interestRate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cok_id", nullable = false, unique = true)
    private InterestRate cok;

    @ManyToOne
    @JoinColumn(name = "bonus_id", nullable = false)
    private Bonus bonus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "grace_period_id", nullable = false, unique = true)
    private GracePeriod gracePeriod;

    @Embedded
    private InitialCosts initialCosts;

    @Embedded
    private PeriodicCosts periodicCosts;

    private Double downPaymentPercentage; // initial payment
    private Double financing; // amount to finance in total
    private Double monthsPaymentTerm; // months to pay

    @Embedded
    private Totals totals;

    @Embedded
    private RentIndicators rentIndicators;

    @OneToMany(mappedBy = "creditApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public CreditApplication() {}

    public CreditApplication(
            CreateCreditApplicationCommand command,
            Client client, Housing housing, Currency currency,
            FinanceEntity financeEntity, InterestRate interestRate,
            InterestRate cok, Bonus bonus, GracePeriod gracePeriod,
            Boolean hasAnotherHousingFinancing) {
        this.realStateCompanyId = new RealStateCompanyId(command.realStateCompanyId());
        this.startDate = command.startDate();
        this.client = client;
        this.housing = housing;
        this.currency = currency;
        this.financeEntity = financeEntity;
        this.interestRate = interestRate;
        this.cok = cok;
        this.bonus = bonus;
        this.gracePeriod = gracePeriod;
        this.initialCosts = new InitialCosts(command.notaryCost(), command.registryCost(),
                financeEntity.getAppraisal(command.appraisal(), housing.getHousingState(), housing.getProvince(), currency),
                financeEntity.getStudyCommission(command.studyCommission(), currency),
                command.activationCommission(), command.professionalFeesCost(),
                financeEntity.getDocumentationFee(command.documentationFee(), currency, housing.getSalePrice()));
        this.periodicCosts = new PeriodicCosts(command.periodicCommission(), command.shippingCosts(),
                command.administrationExpenses(), financeEntity.getLifeInsurance(command.lifeInsurance(), housing.getSalePrice()),
                financeEntity.getRiskInsurance(command.riskInsurance(), this.gracePeriod.getType()), command.monthlyStatementDelivery());
        this.downPaymentPercentage = command.downPaymentPercentage();
        this.financing = calculateFinancing();
        this.monthsPaymentTerm = command.yearsPaymentTerm() * 12;
        this.financeEntityApproved = getFinanceEntityApproved(command.hasCreditHistory(), hasAnotherHousingFinancing);
        if (this.financeEntityApproved.accepted()){
            generatePayments();
            this.rentIndicators = new RentIndicators(this.cok.getTem() * 100, calculateTir(),
                    calculateTceaPercentage(), calculateVan());
            this.totals = new Totals(getTotalInterest(), getTotalCapitalAmortization(),
                    getTotalLifeInsurance(), getTotalRiskInsurance(),
                    getTotalPeriodCommission(), getTotalAdministrationFee());
        } else {
            this.rentIndicators = new RentIndicators(0.0, 0.0,
                    0.0, 0.0);
            this.totals = new Totals(0.0, 0.0,
                    0.0, 0.0,
                    0.0, 0.0);
        }
    }

    private FinanceEntityValidationResult getFinanceEntityApproved(
            Boolean hasCreditHistory, Boolean hasAnotherHousingFinancing)
    {
        boolean isHousingUsed = false;
        boolean isHousingInProject = false;
        if (this.housing.getHousingState() == HousingState.SEGUNDA) isHousingUsed = true;
        if (this.housing.getHousingState() == HousingState.EN_PROYECTO) isHousingInProject = true;
        return this.financeEntity.isFinanceEntityAccepted(
                this.housing.getSalePrice(), this.financing, this.client.getMonthlyIncome(),
                this.downPaymentPercentage, isHousingInProject, this.gracePeriod.getMonths(),
                this.client.getIsDependent(), this.client.getWorkingYears(), hasCreditHistory,
                isHousingUsed, hasAnotherHousingFinancing);
    }

    private Double calculateVan() {
        List<Double> flows = buildCashFlowList();
        double vnaValue = vna(flows);
        return this.financing + vnaValue;
    }

    public double vna(List<Double> cashFlows) {
        double npv = 0.0;
        for (int t = 1; t < cashFlows.size(); t++) {
            npv += cashFlows.get(t) / Math.pow(1 + this.cok.getTem(), t);
        }

        return npv;
    }

    public double calculateTir() {
        return irr(0.01) * 100;
    }

    public double irr(double guess) {
        List<Double> cashFlows = buildCashFlowList();

        double x0 = guess;
        double x1;
        int maxIterations = 1000;
        double tolerance = 1e-7;

        for (int i = 0; i < maxIterations; i++) {
            double fValue = npv(cashFlows, x0);
            double fDerivative = npvDerivative(cashFlows, x0);

            if (Math.abs(fDerivative) < 1e-10) break;
            x1 = x0 - fValue / fDerivative;
            if (Math.abs(x1 - x0) <= tolerance) return x1;
            x0 = x1;
        }
        return x0;
    }

    private List<Double> buildCashFlowList() {
        List<Double> flows = new ArrayList<>();
        double initialFlow = this.financing;
        flows.add(initialFlow);

        for (Payment p : this.payments) {
            flows.add(p.getCashFlow());
        }

        return flows;
    }

    public static double npv(List<Double> cashFlows, double rate) {
        double npv = 0.0;
        for (int t = 0; t < cashFlows.size(); t++) {
            npv += cashFlows.get(t) / Math.pow(1 + rate, t);
        }
        return npv;
    }

    public static double npvDerivative(List<Double> cashFlows, double rate) {
        double derivative = 0.0;
        for (int t = 1; t < cashFlows.size(); t++) {
            derivative += -t * cashFlows.get(t) / Math.pow(1 + rate, t + 1);
        }
        return derivative;
    }

    private Double calculateTceaPercentage() {
        Double tir = calculateTir()/100;

        // Fórmula: TCEA = (1 + TIR)^(cuotas por año) - 1
        double tcea = Math.pow(1 + tir, 12) - 1;
        return tcea * 100;
    }

    private Double getTotalInterest() {
        double interest = 0.0;
        for (Payment p : this.payments) {
            interest += -(p.getFee()-p.getAmortization()-p.getPeriodicCosts().lifeInsurance());
        }
        return interest;
    }

    private Double getTotalCapitalAmortization() {
        double amortization = 0.0;
        for (Payment p : this.payments) {
            amortization += p.getAmortization();
        }
        return -amortization;
    }

    private Double getTotalLifeInsurance() {
        double insurance = 0.0;
        for (Payment p : this.payments) {
            insurance += p.getPeriodicCosts().lifeInsurance();
        }
        return -insurance;
    }

    private Double getTotalRiskInsurance() {
        double insurance = 0.0;
        for (Payment p : this.payments) {
            insurance += p.getPeriodicCosts().riskInsurance();
        }
        return -insurance;
    }

    private Double getTotalPeriodCommission() {
        double commission = 0.0;
        for (Payment p : this.payments) {
            commission += p.getPeriodicCosts().periodicCommission();
        }
        return commission;
    }

    private Double getTotalAdministrationFee() {
        double totalAdministration = 0.0;
        for (Payment p : this.payments) {
            totalAdministration += p.getPeriodicCosts().shippingCosts() + p.getPeriodicCosts().administrationExpenses();
        }
        return totalAdministration;
    }

    private Double calculateFinancing() {   // monto del prestamo
         return this.housing.getSalePrice() - this.downPaymentPercentage/100 * this.housing.getSalePrice()
                 + this.initialCosts.getTotalInitialCost() - this.bonus.getGivenAmount();
    }

    private double pago(double tasa, double nPeriod, double currentValue) {
        double pow = Math.pow(1 + tasa, -nPeriod);
        return - (tasa * currentValue)/(1 - pow);   // consder negative
    }

    public void generatePayments() {
        /**
         * public Payment(Integer orderNumber, Date paymentDate, Double tem,
         *                    GracePeriodType gracePeriodType, Double initialBalance,
         *                    Double interest, Double fee,
         *                    Double amortization, PeriodicCosts periodicCosts,
         *                    Double finalBalance, Double cashFlow) {
         *         this.orderNumber = orderNumber;          -> i
         *         this.paymentDate = paymentDate;          -> paymentDate
         *         this.tem = tem;                          -> this.interestRate.tem
         *         this.gracePeriodType = gracePeriodType;  -> this.gracePeriod.type
         *         this.initialBalance = initialBalance;    -> initialBalance
         *         this.interest = interest;                -> interest
         *         this.fee = fee;                          -> fee
         *         this.amortization = amortization;        -> amortization
         *         this.periodicCosts = periodicCosts;      -> paymentPeriodicCosts
         *              PeriodicCosts(
             *              Double periodicCommission,              -> this.periodicCosts.periodicCommission
             *              Double shippingCosts, // portes         -> this.periodicCosts.shippingCosts
             *              Double administrationExpenses,          -> this.periodicCosts.administrationExpenses
             *              Double lifeInsurance, // desgravamen    -> lifeInsurance
             *              Double riskInsurance,                   -> riskInsurance
         *                  Double monthlyStatementDelivery         -> this.periodicCosts.monthlyStatementDelivery
             *      )
         *         this.finalBalance = finalBalance;        -> finalBalance
         *         this.cashFlow = cashFlow;                -> cashFlow
         *     }
         */
        double finalBalance = this.financing;
        double riskInsurance = - (this.periodicCosts.riskInsurance()/100 * this.housing.getSalePrice())/12;

        for (int i = 1; i <= this.monthsPaymentTerm; i++) {
            double initialBalance = finalBalance;
            double interest = - initialBalance * this.interestRate.getTem();
            LocalDate paymentDate = this.startDate.plusMonths(i-1);
            
            // Periodo de Gracia
            double fee = 0;
            double amortization = 0;
            double cashFlowExtra = 0;
            double lifeInsurance = - initialBalance * this.periodicCosts.lifeInsurance()/100;
            GracePeriodType gracePeriodType = GracePeriodType.NULL;
            if (i <= this.gracePeriod.getMonths()) {
                if (this.gracePeriod.getType() == GracePeriodType.TOTAL) {
                    fee = 0;
                    amortization = 0;
                    finalBalance = initialBalance - interest;
                    gracePeriodType = GracePeriodType.TOTAL;
                } else if (this.gracePeriod.getType() == GracePeriodType.PARCIAL) {
                    fee = interest;
                    amortization = 0;
                    finalBalance = initialBalance + amortization;
                    gracePeriodType = GracePeriodType.PARCIAL;
                }
                cashFlowExtra = lifeInsurance;
            } else {
                fee = pago(this.getInterestRate().getTem() + this.periodicCosts.lifeInsurance()/100,
                        this.monthsPaymentTerm - i + 1, initialBalance);
                amortization = fee - interest - lifeInsurance;
                finalBalance = initialBalance + amortization;
                if (i == this.monthsPaymentTerm) finalBalance = 0.0;
            }

            PeriodicCosts paymentPeriodicCosts = new PeriodicCosts(this.periodicCosts.periodicCommission(),
                    this.periodicCosts.shippingCosts(), this.periodicCosts.administrationExpenses(),
                    lifeInsurance, riskInsurance, this.periodicCosts.monthlyStatementDelivery());

            double cashFlow = fee - paymentPeriodicCosts.totalPeriodicCostsWithoutLifeInsurance() + cashFlowExtra;

            Payment payment = new Payment(i, paymentDate, this.interestRate.getTem(), gracePeriodType,
                    initialBalance, interest, fee, amortization, paymentPeriodicCosts, finalBalance, cashFlow);

            payment.setCreditApplication(this);
            this.payments.add(payment);
        }
    }

    public void removePayment(Payment payment) {
        if (payment == null) return;
        payment.setCreditApplication(null);
        this.payments.remove(payment);
    }
}
