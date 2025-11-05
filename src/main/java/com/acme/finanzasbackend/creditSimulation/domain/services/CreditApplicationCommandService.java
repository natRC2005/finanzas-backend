package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;

public interface CreditApplicationCommandService {
    Long handle(CreateCreditApplicationCommand command);
}
