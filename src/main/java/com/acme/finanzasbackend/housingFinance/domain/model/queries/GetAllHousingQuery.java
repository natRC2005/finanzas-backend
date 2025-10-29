package com.acme.finanzasbackend.housingFinance.domain.model.queries;

public record GetAllHousingQuery(Long realStateCompanyId) {
    public GetAllHousingQuery {
        if (realStateCompanyId == null) {
            throw new IllegalArgumentException("realStateCompanyId cannot be null");
        }
    }
}