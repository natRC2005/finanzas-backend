package com.acme.finanzasbackend.housingFinance.domain.model.commands;

public record DeleteHousingCommand(Long id) {
    public DeleteHousingCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
    }
}
