package com.acme.finanzasbackend.creditSimulation.domain.model.queries;

public record GetInterestRateByIdQuery(Long id) {
    public GetInterestRateByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}
