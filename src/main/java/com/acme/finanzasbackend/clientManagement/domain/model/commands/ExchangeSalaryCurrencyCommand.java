package com.acme.finanzasbackend.clientManagement.domain.model.commands;

public record ExchangeSalaryCurrencyCommand(Long id) {
    public ExchangeSalaryCurrencyCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
    }
}
