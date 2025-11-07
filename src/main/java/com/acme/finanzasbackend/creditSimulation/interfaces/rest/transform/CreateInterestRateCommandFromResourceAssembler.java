package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateInterestRateCommand;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateInterestRateResource;

public class CreateInterestRateCommandFromResourceAssembler {
    public static CreateInterestRateCommand toCommandFromResource(CreateInterestRateResource resource) {
        return new CreateInterestRateCommand(
                resource.type(),
                resource.period(),
                resource.percentage()
        );
    }
}
