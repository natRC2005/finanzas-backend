package com.acme.finanzasbackend.clientManagement.interfaces.rest.transform;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.UpdateClientCommand;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.UpdateClientResource;

public class UpdateClientCommandFromResourceAssembler {
    public static UpdateClientCommand toCommandFromResource(Long id, UpdateClientResource resource) {
        return new UpdateClientCommand(
                id,
                resource.firstname(),
                resource.lastname(),
                resource.dni(),
                resource.age(),
                resource.email(),
                resource.isWorking(),
                resource.dependentsNumber(),
                resource.monthlyIncome(),
                resource.isDependent(),
                resource.workingYears(),
                resource.isIntegrator(),
                resource.currencyId()
        );
    }
}
