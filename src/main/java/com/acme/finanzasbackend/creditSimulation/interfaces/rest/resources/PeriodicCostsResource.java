package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record PeriodicCostsResource(
        Double periodicCommission,
        Double shippingCosts, // portes
        Double administrationExpenses,
        Double lifeInsurance, // desgravamen
        Double riskInsurance,
        Double monthlyStatementDelivery
) {
}
