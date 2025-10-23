package com.acme.finanzasbackend.iam.interfaces.rest.transform;

import com.acme.finanzasbackend.iam.domain.model.commands.SignUpCommand;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(
                resource.companyName(),
                resource.username(),
                resource.ruc(),
                resource.companyEmail(),
                resource.password()
        );
    }
}
