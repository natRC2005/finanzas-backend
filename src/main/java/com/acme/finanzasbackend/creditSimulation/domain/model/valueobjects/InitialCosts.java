package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record InitialCosts(
        Double notaryCost,
        Double registryCost,
        Double appraisal, // tasacion
        Double studyCommission,
        Double activationCommission
) {
    public InitialCosts {}

    public Double getTotalInitialCost() {
        return notaryCost + registryCost + appraisal + studyCommission + activationCommission;
    }
}
