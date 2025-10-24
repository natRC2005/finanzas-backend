package com.acme.finanzasbackend.iam.interfaces.rest.resources;

public record AuthenticatedResource(
        Long id,
        String companyName,
        String username,
        String ruc,
        String companyEmail
) {
}
