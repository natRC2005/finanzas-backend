package com.acme.finanzasbackend.housingFinance.interfaces.rest.resources;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityType;

public record FinanceEntityResource(
        Long id,
        String name,
        FinanceEntityType type,
        Double minimumHousingPrice,
        Double maximumHousingPrice,
        Double minimumFinance,
        Double maximumFinance,
        Double minimumSalary,
        Double minimumDownPaymentPercentage,
        Double maximumDownPaymentPercentage,
        Boolean allowsUsedHousing,
        Integer inProjectMaxGracePeriodMonths,
        Integer generalMaxGracePeriodMonths,
        Integer minimumMonthPaymentTerm,
        Integer maximumMonthPaymentTerm,
        Boolean allowsAnotherHousingFinancing,
        Double minYearsDependentEmploymentTenure,
        Double minYearsIndependentEmploymentTenure,
        Boolean requiresCreditHistory
) {
}
