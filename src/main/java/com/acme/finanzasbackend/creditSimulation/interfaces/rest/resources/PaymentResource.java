package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record PaymentResource(
        Long id,
        Integer orderNumber,
        Double fee,
        Double interest,
        Double amortization,
        Double lifeInsuranceFee,
        Double housingInsuranceFee,
        Double balance
) {
}
