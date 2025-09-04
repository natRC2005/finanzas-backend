package com.acme.finanzasbackend.clientManagement.domain.model.aggregates;

import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Client extends AuditableAbstractAggregateRoot<Client> {
    @NotNull
    @Embedded
    private RealStateCompanyId realStateCompanyId;

    private String firstname;
    private String lastname;
    private String dni;
    private Integer age;
    private String email;
    private Integer dependentsNumber;
    private Boolean isWorking;
    private Double monthlyIncome;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

}
