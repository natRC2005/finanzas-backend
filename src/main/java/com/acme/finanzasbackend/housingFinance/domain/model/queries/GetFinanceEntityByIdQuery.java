package com.acme.finanzasbackend.housingFinance.domain.model.queries;

public record GetFinanceEntityByIdQuery(Long id) {
    public GetFinanceEntityByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}
