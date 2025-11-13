package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingCategory;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;

public record CreateBonusResource(
        Boolean isRequired,
        String housingCategory,
        Double housingSalePrice,
        Currency currency,
        Boolean isIntegrator
) {
    public CreateBonusResource {
        if (isRequired == null) {
            throw new IllegalArgumentException("isRequired cannot be null");
        }
        if (housingSalePrice == null) {
            throw new IllegalArgumentException("housingSalePrice cannot be null");
        }
        if (housingCategory == null) {
            throw new IllegalArgumentException("housingCategory cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }
        if (isIntegrator == null) {
            throw new IllegalArgumentException("isIntegrator cannot be null");
        }
    }
}
