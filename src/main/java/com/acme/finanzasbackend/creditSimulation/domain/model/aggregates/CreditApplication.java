package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Tir;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Van;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityValidationResult;
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

    @Embedded
    private Van van;

    @Embedded
    private Tir tir;

    private Double monthsPaymentTerm; // months to pay

    private Double downPaymentPercentage; // initial payment
    private Double financing; // amount to finance in total

    @OneToMany(mappedBy = "creditApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public CreditApplication() {}

    public CreditApplication(
            CreateCreditApplicationCommand command,
            Client client, Housing housing, Currency currency,
            FinanceEntity financeEntity, FinanceEntityValidationResult isFinanceEntityApproved,
            InterestRate interestRate, Bonus bonus, GracePeriod gracePeriod) {
        this.realStateCompanyId = new RealStateCompanyId(command.realStateCompanyId());
        this.startDate = command.startDate();
        this.client = client;
        this.housing = housing;
        this.currency = currency;
        this.financeEntity = financeEntity;
        this.financeEntityApproved = isFinanceEntityApproved;
        this.interestRate = interestRate;
        this.bonus = bonus;
        this.gracePeriod = gracePeriod;
        this.van = null;
        this.tir = null;
        this.monthsPaymentTerm = command.monthsPaymentTerm();
        this.downPaymentPercentage = command.downPaymentPercentage();
        this.financing = this.housing.getSalePrice() * (1 - this.downPaymentPercentage);
    }

    private Double calculateVan() {
        return 0.00;
    }

    private Double calculateTir() {
        return 0.00;
    }

    public void addPayment(Payment payment) {
        if (payment == null) return;
        payment.setCreditApplication(this);
    }

    public void removePayment(Payment payment) {
        if (payment == null) return;
        payment.setCreditApplication(null);
        this.payments.remove(payment);
    }

    /**
     * Missing tasks
     * - evaluate credit application
     * - create credit application
     * - get credit application by id
     * - create payment plan
     *  -> calculate van & tir
     */

}
