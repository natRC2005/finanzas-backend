package com.acme.finanzasbackend.shared.domain.services;

import com.acme.finanzasbackend.shared.domain.model.commands.SeedCurrencyCommand;

public interface CurrencyCommandService {
    void handle(SeedCurrencyCommand command);
}
