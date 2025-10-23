package com.acme.finanzasbackend.iam.interfaces.rest.resources;

public record RealStateCompanyResource(
        Long id,
        String companyName,
        String username,
        String companyEmail
) {
}
