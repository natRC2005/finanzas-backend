package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Tir(Double tir) {
    public Tir {
        if (tir == null || tir <= 0) {
            throw new IllegalArgumentException("Tir can't be null or zero or negative");
        }
    }
}
