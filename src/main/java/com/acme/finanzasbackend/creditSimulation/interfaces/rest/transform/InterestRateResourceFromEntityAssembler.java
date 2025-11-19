package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.InterestRateResource;

public class InterestRateResourceFromEntityAssembler {
    public static InterestRateResource toResourceFromEntity(InterestRate entity) {
        return new InterestRateResource(
                entity.getId(),
                entity.getType().toString(),
                entity.getPeriod().toString(),
                entity.getPercentage(),
                entity.getNominalCapitalization().toString(),
                entity.getTem()
        );
    }
}
