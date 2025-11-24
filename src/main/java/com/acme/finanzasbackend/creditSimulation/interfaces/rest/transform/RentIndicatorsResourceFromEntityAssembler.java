package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.RentIndicators;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.RentIndicatorsResource;

public class RentIndicatorsResourceFromEntityAssembler {
    public static RentIndicatorsResource toResourceFromEntity(RentIndicators entity) {
        return new RentIndicatorsResource(
                entity.cok() != null ? entity.cok() : null,
                entity.tir() != null ? entity.tir() : null,
                entity.tcea() != null ? entity.tcea() : null,
                entity.van() != null ? entity.van() : null
        );
    }
}
