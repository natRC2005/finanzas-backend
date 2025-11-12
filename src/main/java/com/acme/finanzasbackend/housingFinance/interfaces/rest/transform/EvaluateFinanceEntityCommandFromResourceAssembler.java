package com.acme.finanzasbackend.housingFinance.interfaces.rest.transform;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.EvaluateFinanceEntityCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.EvaluateFinanceEntityResource;

public class EvaluateFinanceEntityCommandFromResourceAssembler {
    public static EvaluateFinanceEntityCommand toCommandFromResource(Long id, EvaluateFinanceEntityResource resource) {
        return new EvaluateFinanceEntityCommand(
                id,
                resource.housingPrice(),
                resource.financeAmount(),
                resource.salary(),
                resource.downPaymentPercentage(),
                resource.isHousingInProject(),
                resource.gracePeriodMonths(),
                resource.isDependent(),
                resource.workingYears(),
                resource.hasCreditHistory(),
                resource.isHousingUsed(),
                resource.hasAnotherHousingFinancing()
        );
    }
}
