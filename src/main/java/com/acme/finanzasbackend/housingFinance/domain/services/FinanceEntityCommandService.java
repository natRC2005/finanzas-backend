package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.EvaluateFinanceEntityCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.SeedFinanceEntitiesCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityValidationResult;

public interface FinanceEntityCommandService {
    void handle(SeedFinanceEntitiesCommand command);
    FinanceEntityValidationResult handle(EvaluateFinanceEntityCommand command);
}
