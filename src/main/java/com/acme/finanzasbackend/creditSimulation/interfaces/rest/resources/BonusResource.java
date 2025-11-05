package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record BonusResource(
        Long id,
        Boolean isApplied,
        Double givenAmount
) {
}
