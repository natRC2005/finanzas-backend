package com.acme.finanzasbackend.housingFinance.domain.model.queries;

public record GetHousingByIdQuery(Long id) {
    public GetHousingByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}
