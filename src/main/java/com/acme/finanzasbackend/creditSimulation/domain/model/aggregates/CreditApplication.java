package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Tir;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Van;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
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

    private Boolean isCreditApproved;
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "housing_id", nullable = false)
    private Housing housing;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "finance_entity_id", nullable = false)
    private FinanceEntity financeEntity;

    private Boolean financeEntityApproved;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "interest_rate_id", nullable = false, unique = true)
    private InterestRate interestRate;

    @ManyToOne
    @JoinColumn(name = "Bonus_id", nullable = false)
    private Bonus bonus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "grace_period_id", nullable = false, unique = true)
    private GracePeriod gracePeriod;

    @Embedded
    private Van van;

    @Embedded
    private Tir tir;

    private Double creditAmount;
    private Double monthlyFee;
    private Double monthsPaymentTerm; // months to pay

    private Double financing; // amount to finance in total
    private Double downPaymentPercentage; // initial payment

    @OneToMany(mappedBy = "creditApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

}
