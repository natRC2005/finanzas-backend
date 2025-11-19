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
            Bonus bonus, GracePeriod gracePeriod,
            Boolean hasAnotherHousingFinancing) {
        this.realStateCompanyId = new RealStateCompanyId(command.realStateCompanyId());
        this.startDate = command.startDate();
        this.client = client;
        this.housing = housing;
        this.currency = currency;
        this.financeEntity = financeEntity;
        this.interestRate = interestRate;
        this.bonus = bonus;
        this.gracePeriod = gracePeriod;
        this.initialCosts = new InitialCosts(command.notaryCost(), command.registryCost(),
                financeEntity.getAppraisal(command.appraisal(), housing.getHousingState(), housing.getProvince(), currency),
                financeEntity.getStudyCommission(command.studyCommission(), currency),
                command.activationCommission(), command.professionalFeesCost(),
                financeEntity.getDocumentationFee(command.documentationFee(), currency, housing.getSalePrice()));
        this.periodicCosts = new PeriodicCosts(command.periodicCommission(), command.shippingCosts(),
                command.administrationExpenses(), command.lifeInsurance(), command.riskInsurance());
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
            this.totals = null;
            this.rentIndicators = null;
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
        return 1.0;
    }

    private Double calculateTir() {
        return 1.0;
    }

    private Double calculateTceaPercentage() {
        return 1.0;
    }

    private Double calculateFinalCok() {
        return 0.0;
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

     private Double calculateFinancing() {
         return 0.0;
     }

    public void generatePayments() {
        /**
         * public Payment(Integer orderNumber, Double tem,
         *                    GracePeriodType gracePeriodType, Double initialBalance,
         *                    Double interest, Double fee,
         *                    Double amortization, PeriodicCosts periodicCosts,
         *                    Double finalBalance) {
         *         this.orderNumber = orderNumber;          -> i
         *         this.tem = tem;                          -> this.interestRate.tem
         *         this.gracePeriodType = gracePeriodType;  -> this.gracePeriod.type
         *         this.initialBalance = initialBalance;    -> initialBalance
         *         this.interest = interest;                -> interest
         *         this.fee = fee;                          ->
         *         this.amortization = amortization;
         *         this.periodicCosts = periodicCosts;
         *              PeriodicCosts(
             *              Double periodicCommission,
             *              Double shippingCosts, // portes
             *              Double administrationExpenses,
             *              Double lifeInsurance, // desgravamen
             *              Double riskInsurance
             *      )
         *         this.finalBalance = finalBalance;
         *         this.cashFlow = this.initialBalance - this.finalBalance;
         *     }
         */
        double finalBalance = this.financing;

        for (int i = 1; i <= this.monthsPaymentTerm; i++) {
            double initialBalance = finalBalance;
            double interest = initialBalance * this.interestRate.getTem();
            
            // Periodo de Gracia
            double fee;
            double amortization;
            if (i <= this.gracePeriod.getMonths()) {
                if (this.gracePeriod.getType() == GracePeriodType.TOTAL) {
                    fee = 0;
                    amortization = 0;
                } else if (this.gracePeriod.getType() == GracePeriodType.PARCIAL) {
                    fee = interest;
                    amortization = 0;
                }
            } else {

            }
            
            // Create the Payment

            // falta valor de finalBalance
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
     */

}
