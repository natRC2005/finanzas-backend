package com.acme.finanzasbackend.iam.interfaces.rest.transform;

import com.acme.finanzasbackend.iam.domain.model.commands.UpdateRealStateCompanyCommand;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.UpdateRealStateCompanyResource;

public class UpdateRealStateCompanyCommandFromResourceAssembler {
    public static UpdateRealStateCompanyCommand toCommandFromResource(Long id, UpdateRealStateCompanyResource resource) {
        return new UpdateRealStateCompanyCommand(
                id,
                resource.companyName(),
                resource.username(),
                resource.ruc(),
                resource.companyEmail(),
                resource.password()
        );
    }
}
