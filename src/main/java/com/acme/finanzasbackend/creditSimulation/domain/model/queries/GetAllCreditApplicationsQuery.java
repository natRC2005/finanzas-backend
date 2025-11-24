package com.acme.finanzasbackend.creditSimulation.domain.model.queries;

public record GetAllCreditApplicationsQuery(Long realStateCompanyId) {
    public GetAllCreditApplicationsQuery{
        if (realStateCompanyId == null) {
            throw new IllegalArgumentException("realStateCompanyId cannot be null");
        }
    }
}
