package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.InitialCosts;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.InitialCostsResource;

public class InitialCostsResourceFromEntityAssembler {
    public static InitialCostsResource toResourceFromEntity(InitialCosts entity) {
        return new InitialCostsResource(
                entity.notaryCost() != null ? entity.notaryCost() : null,
                entity.registryCost() != null ? entity.registryCost() : null,
                entity.appraisal() != null ? entity.appraisal() : null,
                entity.studyCommission() != null ? entity.studyCommission() : null,
                entity.activationCommission() != null ? entity.activationCommission() : null
        );
    }
}
