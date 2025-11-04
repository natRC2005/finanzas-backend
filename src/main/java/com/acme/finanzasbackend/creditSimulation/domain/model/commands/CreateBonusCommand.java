package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;

public record CreateBonusCommand(
        Boolean isRequired,
        HousingState housingState,
        Double housingSalePrice
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
    }
}
