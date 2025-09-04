package com.acme.finanzasbackend.shared.domain.model.entities;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Currency extends AuditableModel {
    private String code;
    private String name;
    private String symbol;
}
