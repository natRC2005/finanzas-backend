package com.acme.finanzasbackend.iam.domain.model.aggregates;

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
}
