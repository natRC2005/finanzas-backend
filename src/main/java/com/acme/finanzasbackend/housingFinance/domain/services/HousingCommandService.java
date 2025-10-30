package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.DeleteHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.UpdateHousingCommand;

import java.util.Optional;

public interface HousingCommandService {
    Long handle(CreateHousingCommand command);
    Optional<Housing> handle(UpdateHousingCommand command);
    Optional<Housing> handle(DeleteHousingCommand command);
}
