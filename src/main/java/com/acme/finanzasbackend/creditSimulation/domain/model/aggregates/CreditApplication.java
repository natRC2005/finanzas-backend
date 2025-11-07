package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.GracePeriodType;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Tir;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Van;
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
public class CreditApplication extends AuditableAbstractAggregateRoot<CreditApplication> {  // save with local storage
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

    private Double monthlyLifeInsuranceRate;
    private Double monthlyHousingInsuranceRate;

    @Embedded
    private Van van;

    @Embedded
    private Tir tir;

    private Double monthsPaymentTerm; // months to pay -> // UPDATE -> ask for years, work on moths

    private Double downPaymentPercentage; // initial payment
    private Double financing; // amount to finance in total
    private Double tceaPercentage;

    // UPDATE -> Cok periodo
    //  -> create entity -> RentIndicators (van, tir, tcea, van)

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
        this.monthlyLifeInsuranceRate = command.monthlyLifeInsuranceRate();
        this.monthlyHousingInsuranceRate  = command.monthlyHousingInsuranceRate();
        this.monthsPaymentTerm = command.monthsPaymentTerm();
        this.downPaymentPercentage = command.downPaymentPercentage();
        this.financing = calculateFinancing();
        this.financeEntityApproved = getFinanceEntityApproved(command.hasCreditHistory(), hasAnotherHousingFinancing);
        if (this.financeEntityApproved.accepted()){
            generatePayments();
            this.van = calculateVan();
            this.tir = calculateTir();
            this.tceaPercentage = calculateTceaPercentage();
        } else {
            this.van = null;
            this.tir = null;
            this.tceaPercentage = null;
        }
    }

    private FinanceEntityValidationResult getFinanceEntityApproved(
            Boolean hasCreditHistory, Boolean hasAnotherHousingFinancing)
    {
        boolean isHousingUsed = false;
        if (this.housing.getHousingState() == HousingState.SEGUNDA) isHousingUsed = true;
        return this.financeEntity.isFinanceEntityAccepted(
                this.housing.getSalePrice(), this.financing, this.client.getMonthlyIncome(),
                this.downPaymentPercentage, this.housing.getHousingState(), this.gracePeriod.getMonths(),
                this.client.getIsDependent(), this.client.getWorkingYears(), hasCreditHistory,
                isHousingUsed, hasAnotherHousingFinancing);
    }

    private Van calculateVan() {
        if (interestRate == null || payments == null || payments.isEmpty()) return null;

        double tasaMensual = interestRate.getEffectiveMonthlyRate() / 100;
        double van = -financing; // flujo inicial negativo

        for (Payment payment : payments) {
            int periodo = payment.getOrderNumber();
            double flujo = payment.getFee()
                    + (payment.getLifeInsuranceFee() != null ? payment.getLifeInsuranceFee() : 0.0)
                    + (payment.getHousingInsuranceFee() != null ? payment.getHousingInsuranceFee() : 0.0);
            van += flujo / Math.pow(1 + tasaMensual, periodo);
        }

        return new Van(van);
    }

    private Tir calculateTir() {
        if (payments == null || payments.isEmpty() || financing == null || financing <= 0) return null;

        double guessRate = 0.01; // 1% inicial
        double precision = 1e-7;
        int maxIterations = 10_000;
        double rate = guessRate;

        for (int i = 0; i < maxIterations; i++) {
            double fValue = -financing;
            double fDerivative = 0.0;

            for (Payment payment : payments) {
                int t = payment.getOrderNumber();
                double flujo = payment.getFee()
                        + (payment.getLifeInsuranceFee() != null ? payment.getLifeInsuranceFee() : 0.0)
                        + (payment.getHousingInsuranceFee() != null ? payment.getHousingInsuranceFee() : 0.0);
                fValue += flujo / Math.pow(1 + rate, t);
                fDerivative += -t * flujo / Math.pow(1 + rate, t + 1);
            }

            if (Math.abs(fDerivative) < 1e-10) break; // evitar división por 0

            double newRate = rate - fValue / fDerivative;
            if (Math.abs(newRate - rate) < precision) {
                return new Tir(newRate);
            }

            rate = newRate;
        }

        return new Tir(rate);
    }

    private Double calculateTceaPercentage() {
        if (tir == null || tir.tir() == null) return 0.0;

        double tirMensual = tir.tir();

        return Math.pow(1 + tirMensual, 12) - 1;
    }

     private Double calculateFinancing() {
         if (this.housing == null || this.bonus == null || this.downPaymentPercentage == null)
            return 0.0;

         double salePrice = this.housing.getSalePrice();
         double initialPayment = salePrice * (downPaymentPercentage / 100);
         double bonusAmount = this.bonus.getGivenAmount();
         double financing = salePrice - initialPayment - bonusAmount;
         return Math.max(financing, 0.0);
     }

    public void generatePayments() {
        if (interestRate == null || financing == null || monthsPaymentTerm == null) return;

        // 1️⃣ Obtener tasa efectiva mensual desde InterestRate
        double tasaMensual = interestRate.getEffectiveMonthlyRate() / 100;

        // 2️⃣ Datos básicos
        int n = monthsPaymentTerm.intValue();
        double montoFinanciado = financing;
        double saldo = montoFinanciado;

        // 3️⃣ Periodo de gracia
        int graceMonths = gracePeriod != null ? gracePeriod.getMonths() : 0;
        GracePeriodType graceType = gracePeriod != null ? gracePeriod.getType() : null;

        this.payments.clear();

        // 4️⃣ Cuota base (sin seguros)
        double cuotaBase = (montoFinanciado * tasaMensual * Math.pow(1 + tasaMensual, n)) /
                (Math.pow(1 + tasaMensual, n) - 1);

        // 5️⃣ Generar cronograma de pagos
        for (int i = 1; i <= n; i++) {
            double interes = saldo * tasaMensual;

            // ✅ Seguros calculados sobre saldos o montos fijos
            double lifeInsuranceFee = saldo * (monthlyLifeInsuranceRate / 100);
            double housingInsuranceFee = financing * (monthlyHousingInsuranceRate / 100);

            double amortizacion;
            double totalCuota;

            // 🔸 Durante el periodo de gracia
            if (i <= graceMonths) {
                if (graceType == GracePeriodType.TOTAL && saldo > 0) {
                    // No se paga nada, interés se capitaliza
                    saldo += interes;
                    amortizacion = 0.0;
                    totalCuota = 0.0;
                } else if (graceType == GracePeriodType.PARCIAL) {
                    // Solo se pagan intereses + seguros
                    amortizacion = 0.0;
                    totalCuota = interes + lifeInsuranceFee + housingInsuranceFee;
                } else {
                    // Caso no definido → pago normal
                    amortizacion = cuotaBase - interes;
                    saldo -= amortizacion;
                    totalCuota = cuotaBase + lifeInsuranceFee + housingInsuranceFee;
                }
            }
            // 🔹 Después del periodo de gracia
            else {
                if (i == graceMonths + 1 && graceType == GracePeriodType.TOTAL && graceMonths > 0) {
                    double saldoPostGracia = saldo;
                    int remainingMonths = n - graceMonths;
                    cuotaBase = (saldoPostGracia * tasaMensual * Math.pow(1 + tasaMensual, remainingMonths)) /
                            (Math.pow(1 + tasaMensual, remainingMonths) - 1);
                }
                amortizacion = cuotaBase - interes;
                saldo -= amortizacion;
                if (saldo < 0) saldo = 0.0;
                totalCuota = cuotaBase + lifeInsuranceFee + housingInsuranceFee;
            }

            // 🧾 Crear la cuota
            Payment payment = new Payment(
                    i,                      // número de cuota
                    totalCuota,             // cuota total a pagar
                    interes,                // interés del mes
                    amortizacion,           // amortización del mes
                    lifeInsuranceFee,       // seguro desgravamen
                    housingInsuranceFee,    // seguro inmueble
                    saldo                   // saldo restante
            );

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
     * - evaluate credit application - DONE TO CHECK
     * - create credit application
     * - get credit application by id
     * - create payment plan - DONE TO CHECK
     *  -> calculate van & tir - DONE TO CHECK
     *
     *  MISSING
     *  - check exchange currency functionality
     *  - update credit evaluation failed
     *  - get all credit evaluations
     *  - check credit validation approval
     *
     *  // UPDATE -> Add variables -> completely refactor values calculus
     *      -> remember to consider all kinds of effective rates
     *      -> adapt the whole excel to code (cries)
     */

}
