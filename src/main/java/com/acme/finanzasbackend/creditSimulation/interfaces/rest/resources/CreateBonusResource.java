package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;

public record CreateBonusResource(
        Boolean isRequired,
        String housingState,
        Double housingSalePrice
) {
    public CreateBonusResource {
        if (isRequired == null) {
            throw new IllegalArgumentException("isRequired cannot be null");
        }
        if (housingSalePrice == null) {
            throw new IllegalArgumentException("housingSalePrice cannot be null");
        }
        if (housingState == null) {
            throw new IllegalArgumentException("housingState cannot be null");
        }
    }
}
