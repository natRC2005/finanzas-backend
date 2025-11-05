package com.acme.finanzasbackend.creditSimulation.domain.model.queries;

public record GetCreditApplicationByIdQuery(Long id) {
    public GetCreditApplicationByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}
