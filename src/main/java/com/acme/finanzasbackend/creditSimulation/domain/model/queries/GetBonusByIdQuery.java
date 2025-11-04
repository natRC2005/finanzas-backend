package com.acme.finanzasbackend.creditSimulation.domain.model.queries;

public record GetBonusByIdQuery(Long id) {
    public GetBonusByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}