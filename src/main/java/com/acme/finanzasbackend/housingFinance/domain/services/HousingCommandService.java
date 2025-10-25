package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;

public interface HousingCommandService {
    Long handle(CreateHousingCommand command);
}
