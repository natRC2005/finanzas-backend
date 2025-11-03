package com.acme.finanzasbackend.housingFinance.domain.model.commands;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;

public record EvaluateFinanceEntityCommand(
        Long id,
        Double housingPrice,
        Double financeAmount,
        Double salary,
        Double downPaymentPercentage,
        HousingState housingState,
        Integer gracePeriodMonths,
        Boolean isDependent,
        Double workingYears,
        Boolean hasCreditHistory,
        Boolean isHousingUsed,
        Boolean hasAnotherHousingFinancing
) {
    public EvaluateFinanceEntityCommand {
        if (id == null)
            throw new IllegalArgumentException("Id can't be null");
        if (housingPrice == null || housingPrice <= 0)
            throw new IllegalArgumentException("HousingPrice cannot be null or negative");
        if (financeAmount == null || financeAmount <= 0)
            throw new IllegalArgumentException("FinanceAmount cannot be null or negative");
        if (downPaymentPercentage == null || downPaymentPercentage < 0)
            throw new IllegalArgumentException("DownPaymentPercentage cannot be null or negative");
        if (housingState == null)
            throw new IllegalArgumentException("HousingState cannot be null");
        if (gracePeriodMonths == null || gracePeriodMonths < 0)
            throw new IllegalArgumentException("GracePeriodMonths cannot be null or negative");
        if (isDependent == null)
            throw new IllegalArgumentException("isDependent cannot be null");
        if (workingYears == null || workingYears < 0)
            throw new IllegalArgumentException("workingYears cannot be null or negative");
        if (hasCreditHistory == null)
            throw new IllegalArgumentException("hasCreditHistory cannot be null");
        if (isHousingUsed == null)
            throw new IllegalArgumentException("isHousingUsed cannot be null");
        if (hasAnotherHousingFinancing == null)
            throw new IllegalArgumentException("hasAnotherHousingFinancing cannot be null");
    }
}
