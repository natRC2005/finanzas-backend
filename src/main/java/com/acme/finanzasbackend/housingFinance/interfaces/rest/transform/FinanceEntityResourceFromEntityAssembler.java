package com.acme.finanzasbackend.housingFinance.interfaces.rest.transform;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.FinanceEntityResource;

public class FinanceEntityResourceFromEntityAssembler {
    public static FinanceEntityResource toResourceFromEntity(FinanceEntity entity) {
        return new FinanceEntityResource(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getMinimumHousingPrice(),
                entity.getMaximumHousingPrice(),
                entity.getMinimumFinance(),
                entity.getMaximumFinance(),
                entity.getMinimumSalary(),
                entity.getMinimumDownPaymentPercentage(),
                entity.getMaximumDownPaymentPercentage(),
                entity.getAllowsUsedHousing(),
                entity.getInProjectMaxGracePeriodMonths(),
                entity.getGeneralMaxGracePeriodMonths(),
                entity.getMinimumMonthPaymentTerm(),
                entity.getMaximumMonthPaymentTerm(),
                entity.getAllowsAnotherHousingFinancing(),
                entity.getMinYearsDependentEmploymentTenure(),
                entity.getMaxYearsIndependentEmploymentTenure(),
                entity.getRequiresCreditHistory()
        );
    }
}
