package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record InitialCostsResource(
        Double notaryCost,
        Double registryCost,
        Double appraisal, // tasacion
        Double studyCommission,
        Double activationCommission,
        Double professionalFeesCost, // gatos por honorarios profesionales
        Double documentationFee
) {
}
