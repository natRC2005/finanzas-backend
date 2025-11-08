package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RentIndicators(
        Double cok,
        Double tir,
        Double tcea,
        Double var
) {
    public RentIndicators {}
}
