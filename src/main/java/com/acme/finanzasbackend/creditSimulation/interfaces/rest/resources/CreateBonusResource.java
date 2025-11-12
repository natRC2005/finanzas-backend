package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;

public record CreateBonusResource(
        Boolean isRequired,
        String housingState,
        Double housingSalePrice,
        Currency currency
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
        if (currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }
    }
}
