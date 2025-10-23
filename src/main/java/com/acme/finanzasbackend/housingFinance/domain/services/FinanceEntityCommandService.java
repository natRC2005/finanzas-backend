package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.SeedFinanceEntitiesCommand;

public interface FinanceEntityCommandService {
    void handle(SeedFinanceEntitiesCommand command);
}
