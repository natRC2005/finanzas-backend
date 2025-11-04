package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateBonusCommand;

public interface BonusCommandService {
    Long handle(CreateBonusCommand command);
}
