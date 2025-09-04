package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Bfh(double uit, double amount) {
    public Bfh {
        if (uit < 0) {
            throw new IllegalArgumentException("The UIT amount cannot be less than 0");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("The amount cannot be less than 0");
        }
    }
}
