package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.BonusResource;

public class BonusResourceFromEntityAssembler {
    public static BonusResource toResourceFromEntity(Bonus entity) {
        return new BonusResource(
                entity.getId(),
                entity.getIsApplied(),
                entity.getGivenAmount()
        );
    }
}
