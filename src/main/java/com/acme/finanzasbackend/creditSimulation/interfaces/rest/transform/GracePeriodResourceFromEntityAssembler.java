package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.GracePeriodResource;

public class GracePeriodResourceFromEntityAssembler {
    public static GracePeriodResource toResourceFromEntity(GracePeriod entity) {
        return new GracePeriodResource(
                entity.getId(),
                entity.getType().toString(),
                entity.getMonths()
        );
    }
}
