package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record InterestRateResource(
        Long id,
        String type,
        String capitalization,
        Double percentage
) {
}
