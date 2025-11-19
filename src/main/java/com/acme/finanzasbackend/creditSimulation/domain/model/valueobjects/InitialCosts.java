package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record InitialCosts(
        Double notaryCost, // gastos notariales
        Double registryCost, // gastos registrales
        Double appraisal, // tasacion
        Double studyCommission, // Comisión de Estudio de pólizas endosadas
        Double activationCommission,
        Double professionalFeesCost, // gatos por honorarios profesionales
        Double documentationFee // Derecho de documentación
) {
    public InitialCosts {}

    public Double getTotalInitialCost() {
        return notaryCost + registryCost + appraisal +
                studyCommission + activationCommission +
                professionalFeesCost  + documentationFee;
    }
}
