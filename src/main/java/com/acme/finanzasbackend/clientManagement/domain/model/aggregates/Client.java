package com.acme.finanzasbackend.clientManagement.domain.model.aggregates;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.UpdateClientCommand;
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
    private Boolean isWorking;
    private Integer dependentsNumber;
    private Double monthlyIncome;

    private Boolean isDependent;
    private Double workingYears;

    private Boolean isIntegrator;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public Client() {}

    public Client(CreateClientCommand command, Currency currency) {
        this.realStateCompanyId = new RealStateCompanyId(command.realStateCompanyId());
        this.firstname = command.firstname();
        this.lastname = command.lastname();
        this.dni = command.dni();
        this.age = command.age();
        this.email = command.email();
        this.isWorking = command.isWorking();
        this.dependentsNumber = command.dependentsNumber();
        this.monthlyIncome = command.monthlyIncome();
        this.isDependent = command.isDependent();
        this.workingYears = command.workingYears();
        this.isIntegrator = command.isIntegrator();
        this.currency = currency;
        Double salary = this.monthlyIncome;
        if (this.currency.getId() == 2) salary = this.currency.exchangeCurrency(monthlyIncome);
        if (this.age >= 65 || salary <= 4746.00) this.isIntegrator  = true;
    }

    public void modifyClient(UpdateClientCommand command, Currency currency) {
        this.firstname = command.firstname();
        this.lastname = command.lastname();
        this.dni = command.dni();
        this.age = command.age();
        this.email = command.email();
        this.isWorking = command.isWorking();
        this.dependentsNumber = command.dependentsNumber();
        this.monthlyIncome = command.monthlyIncome();
        this.isDependent = command.isDependent();
        this.workingYears = command.workingYears();
        this.isIntegrator = command.isIntegrator();
        this.currency = currency;
        Double salary = this.monthlyIncome;
        if (this.currency.getId() == 2) salary = this.currency.exchangeCurrency(monthlyIncome);
        if (this.age >= 65 || salary <= 4746.00) this.isIntegrator  = true;
    }

    public void exchangeSalaryCurrency(Currency currency) {
        if (this.currency != currency) {
            this.monthlyIncome = this.currency.exchangeCurrency(this.monthlyIncome);
            this.currency = currency;
        }
    }
}
