package com.acme.finanzasbackend.iam.domain.model.queries;

public record GetRealStateCompanyByIdQuery(Long id) {
    public GetRealStateCompanyByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }
}
