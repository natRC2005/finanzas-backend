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
            this.totals = new Totals(0.0, 0.0,
                    0.0, 0.0,
                    0.0, 0.0);
            this.rentIndicators = new RentIndicators(0.0, 0.0,
                    0.0, 0.0);
        } else {
            this.totals = new Totals(0.0, 0.0,
                    0.0, 0.0,
                    0.0, 0.0);
            this.rentIndicators = new RentIndicators(0.0, 0.0,
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
        Double tasaDescuentoMensual = calculateFinalCok();

        // Verificación básica
        if (this.payments.isEmpty() || this.financing == null) {
            return 0.0;
        }

        // 2. Agregar el Desembolso Inicial (Flujo Positivo en t=0), equivalente a S25
        double van = -this.financing;

        // 3. Sumar el Valor Actual de los Flujos Futuros (equivalente a VNA(COKi; Flujo))
        // La iteración comienza en el índice 0, que corresponde al período t=1
        for (int t = 0; t < this.payments.size(); t++) {
            Payment payment = this.payments.get(t);
            // El período 't' en la fórmula de descuento debe ser t+1
            int tPeriodo = t + 1;
            // cashFlow debe ser NEGATIVO
            Double flujoNeto = payment.getCashFlow();
            if (flujoNeto == null) continue;
            // Fórmula de Descuento: Flujo_t / (1 + r)^t
            double denominador = Math.pow(1 + tasaDescuentoMensual, tPeriodo);
            double valorActualFlujo = flujoNeto / denominador;
            van += valorActualFlujo;
        }
        return van; // 1. Obtener la Tasa de Descuento periódica (TEM COK)
       
    }

    private Double calculateTir() {
        List<Double> cashFlows = new ArrayList<>();

        // El primer flujo es el financiamiento negativo (desembolso inicial)
        // Incluye el monto del préstamo menos los costos iniciales que ya pagaste
        cashFlows.add(-this.financing);

        // Agregar los flujos de caja de cada pago (cuotas + costos periódicos)
        for (Payment payment : this.payments) {
            double cashFlow = payment.getFee()
                    + payment.getPeriodicCosts().getTotalPeriodicCosts();
            cashFlows.add(cashFlow);
        }

        // Parámetros para el cálculo del TIR
        double guess = 0.01; // Tasa inicial (1% mensual)
        int maxIterations = 100;
        double tolerance = 0.00001;
        double rate = guess;

        for (int i = 0; i < maxIterations; i++) {
            double npv = 0.0;
            double dnpv = 0.0;

            for (int j = 0; j < cashFlows.size(); j++) {
                npv += cashFlows.get(j) / Math.pow(1 + rate, j);
                dnpv += -j * cashFlows.get(j) / Math.pow(1 + rate, j + 1);
            }

            if (Math.abs(npv) < tolerance) {
                return rate; // TIR mensual
            }

            if (Math.abs(dnpv) < tolerance) {
                return null;
            }

            rate = rate - npv / dnpv;

            if (rate < -0.99 || Double.isNaN(rate) || Double.isInfinite(rate)) {
                return null;
            }
        }
        return null;
    }

    private Double calculateTceaPercentage() {
        Double tir = calculateTir();
        if (tir == null) {
            return null; // Si no se pudo calcular el TIR, no se puede calcular TCEA
        }
        // I6 = número de cuotas por año (12 meses)
        double cuotasPorAnio = 12.0;

        // Fórmula: TCEA = (1 + TIR)^(cuotas por año) - 1
        double tcea = Math.pow(1 + tir, cuotasPorAnio) - 1;

        return tcea * 100;
    }

    private Double calculateFinalCok() {
        return 1.0;
    }

    private Double getTotalInterest() {
        return 0.0;
    }

    private Double getTotalCapitalAmortization() {
        return 0.0;
    }

    private Double getTotalLifeInsurance() {
        return 0.0;
    }

    private Double getTotalRiskInsurance() {
        return 0.0;
    }

    private Double getTotalPeriodCommission() {
        return 0.0;
    }

    private Double getTotalAdministrationFee() {
        return 0.0;
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

    /**
     * Missing tasks
     * - create payment plan
     *  -> calculate van & tir
     *
     *  MISSING
     *  - update credit evaluation failed
     *  - get all credit evaluations
     *
     *  // UPDATE -> Add variables -> completely refactor values calculus
     *      -> remember to consider all kinds of effective rates
     *      -> adapt the whole Excel to code (cries)
     *      -> FIRST -> Check InterestRate use
     *
     *  FOR SATURDAY
     *  -> Calculate cashFlow -> JUST MISSING THIS FOR PAYMENTSSS - CHECK
     *      - Add sum function for PeriodicCosts - CHECK
     *      - Add calculateFinancing()
     *      - Create PeriodicCosts object for each payment - CHECK
     */

}
