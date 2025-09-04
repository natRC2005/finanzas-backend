package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Van(Double van) {
    public Van {
        if (van == null || van <= 0) {
            throw new IllegalArgumentException("Van can't be null or zero or negative");
        }
    }
}
