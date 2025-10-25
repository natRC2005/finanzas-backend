package com.acme.finanzasbackend.clientManagement.domain.model.queries;

public record GetClientByIdQuery(Long id) {
    public GetClientByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than one");
        }
    }
}
