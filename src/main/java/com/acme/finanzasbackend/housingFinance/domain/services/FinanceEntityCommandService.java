package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.EvaluateFinanceEntityCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.SeedFinanceEntitiesCommand;

public interface FinanceEntityCommandService {
    void handle(SeedFinanceEntitiesCommand command);
    Boolean handle(EvaluateFinanceEntityCommand command);
}
