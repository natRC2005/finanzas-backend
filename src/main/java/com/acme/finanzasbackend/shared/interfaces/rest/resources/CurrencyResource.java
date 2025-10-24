package com.acme.finanzasbackend.shared.interfaces.rest.resources;

public record CurrencyResource(
        Long id,
        String code,
        String name,
        String symbol
) {
}
