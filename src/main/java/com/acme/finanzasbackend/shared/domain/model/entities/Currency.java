package com.acme.finanzasbackend.shared.domain.model.entities;

import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Currency extends AuditableModel {
    private String code;
    private String name;
    private String symbol;

    public Currency() {}

    public Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public Double exchangeCurrency(Double amount) {
        Double exchangeRate = 3.39;
        if (Objects.equals(this.code, "PEN")) {
            return amount / exchangeRate;
        }
        return amount * exchangeRate;
    }
}
