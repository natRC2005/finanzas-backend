package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record CreditApplicationResource(
        Long id,
        Long realStateCompanyId,
        LocalDate startDate,
        Long clientId,
        Long housingId,
        Long currencyId,
        Long financeEntityId,
        Boolean financeEntityApproved,
        String financeEntityReason,
        InterestRateResource interestRate,
        Double monthlyLifeInsuranceRate,
        Double monthlyHousingInsuranceRate,
        Double monthsPaymentTerm,
        Double downPaymentPercentage,
        Double financing,
        Double tceaPercentage,
        Double van,
        Double tir,
        BonusResource bonus,
        GracePeriodResource gracePeriod,
        List<PaymentResource> payments
) {
}
