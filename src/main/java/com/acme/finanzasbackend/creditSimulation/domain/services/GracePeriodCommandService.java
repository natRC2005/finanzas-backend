package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateGracePeriodCommand;

public interface GracePeriodCommandService {
    Long handle(CreateGracePeriodCommand command);
}
