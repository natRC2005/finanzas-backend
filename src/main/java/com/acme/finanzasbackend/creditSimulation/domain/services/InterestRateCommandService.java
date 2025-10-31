package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateInterestRateCommand;

public interface InterestRateCommandService {
    Long handle(CreateInterestRateCommand command);
}
