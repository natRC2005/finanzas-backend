package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record TotalsResource(
        Double totalInterest,
        Double totalCapitalAmortization,
        Double totaLifeInsurance,
        Double totalRiskInsurance,
        Double totalPeriodicCommission,
        Double totalAdministrationFees
) {
}
