package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;

public record CreateBonusCommand(
        Boolean isRequired,
        HousingState housingState,
        Double housingSalePrice,
        Currency currency
) {
    public CreateBonusCommand {
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
