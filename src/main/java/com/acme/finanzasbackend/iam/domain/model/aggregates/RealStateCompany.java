package com.acme.finanzasbackend.iam.domain.model.aggregates;

import com.acme.finanzasbackend.iam.domain.model.commands.SignUpCommand;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RealStateCompany extends AuditableAbstractAggregateRoot<RealStateCompany> {
    private String companyName;
    private String username;
    private String ruc;
    private String companyEmail;
    private String password;
    private Boolean isLogged;

    public RealStateCompany() {}

    public RealStateCompany(SignUpCommand command, String password) {
        this.companyName = command.companyName();
        this.username = command.username();
        this.ruc = command.ruc();
        this.companyEmail = command.companyEmail();
        this.password = password;
        this.isLogged = false;
    }
}
