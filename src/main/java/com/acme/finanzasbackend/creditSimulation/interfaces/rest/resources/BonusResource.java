package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.BonusType;

public record BonusResource(
        Long id,
        Boolean isApplied,
        Double givenAmount,
        String bonusType
) {
}
