package com.acme.finanzasbackend.shared.domain.model.queries;

public record GetCurrencyByIdQuery(Long id) {
    public GetCurrencyByIdQuery {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("id must not be null or negative");
        }
    }
}
