package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

public record DeleteCreditApplicationCommand(Long id) {
    public DeleteCreditApplicationCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
    }
}
