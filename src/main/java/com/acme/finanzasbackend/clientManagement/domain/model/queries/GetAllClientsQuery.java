package com.acme.finanzasbackend.clientManagement.domain.model.queries;

public record GetAllClientsQuery(Long realStateCompanyId) {
    public GetAllClientsQuery {
        if (realStateCompanyId == null) {
            throw new IllegalArgumentException("realStateCompanyId cannot be null");
        }
    }
}
