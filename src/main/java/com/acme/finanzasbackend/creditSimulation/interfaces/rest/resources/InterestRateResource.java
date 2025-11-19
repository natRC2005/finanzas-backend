package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record InterestRateResource(
        Long id,
        String type,
        String period,
        Double percentage,
        String nominalCapitalization,
        Double tem
) {
}
