package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateGracePeriodCommand;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateGracePeriodResource;

public class CreateGracePeriodCommandFromResourceAssembler {
    public static CreateGracePeriodCommand toCommandFromResource(CreateGracePeriodResource resource) {
        return new CreateGracePeriodCommand(
                resource.type(),
                resource.months()
        );
    }
}
