package com.acme.finanzasbackend.clientManagement.interfaces.rest.transform;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.CreateClientResource;

public class CreateClientCommandFromResourceAssembler {
    public static CreateClientCommand toCommandFromResource(CreateClientResource resource) {
        return new CreateClientCommand(
                resource.realStateCompanyId(),
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
                resource.currencyId()
        );
    }
}
