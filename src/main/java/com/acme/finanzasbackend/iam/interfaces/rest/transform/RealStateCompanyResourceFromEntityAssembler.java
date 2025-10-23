package com.acme.finanzasbackend.iam.interfaces.rest.transform;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.RealStateCompanyResource;

public class RealStateCompanyResourceFromEntityAssembler {
    public static RealStateCompanyResource toResourceFromEntity(RealStateCompany entity) {
        return new RealStateCompanyResource(
                entity.getId(),
                entity.getCompanyName(),
                entity.getUsername(),
                entity.getCompanyEmail()
        );
    }
}
