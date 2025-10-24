package com.acme.finanzasbackend.iam.interfaces.rest.transform;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.AuthenticatedResource;

public class AuthenticatedResourceFromEntityAssembler {
    public static AuthenticatedResource toResourceFromEntity(RealStateCompany entity) {
        return new AuthenticatedResource(
                entity.getId(),
                entity.getCompanyName(),
                entity.getUsername(),
                entity.getRuc(),
                entity.getCompanyEmail()
        );
    }
}
