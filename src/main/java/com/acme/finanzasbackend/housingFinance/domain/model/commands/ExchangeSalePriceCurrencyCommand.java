package com.acme.finanzasbackend.housingFinance.domain.model.commands;

public record ExchangeSalePriceCurrencyCommand(Long id) {
    public ExchangeSalePriceCurrencyCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
    }
}