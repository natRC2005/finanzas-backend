package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.DeleteCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.UpdateCreditApplicationCommand;

import java.util.Optional;

public interface CreditApplicationCommandService {
    Long handle(CreateCreditApplicationCommand command);
    Optional<CreditApplication> handle(DeleteCreditApplicationCommand command);
    Optional<CreditApplication> handle(UpdateCreditApplicationCommand command);
}
