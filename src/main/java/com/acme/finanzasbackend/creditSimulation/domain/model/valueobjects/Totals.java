package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

public record Totals(
        Double totalInterest,
        Double totalCapitalAmortization,
        Double totaLifeInsurance,
        Double totalRiskInsurance,
        Double totalPeriodicCommission,
        Double totalAdministrationFees
) {
    public Totals {}
}
