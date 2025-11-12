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
        GracePeriodResource gracePeriod,
        InitialCostsResource initialCosts,
        PeriodicCostsResource periodicCosts,
        Double downPaymentPercentage,
        Double financing,
        Double monthsPaymentTerm,
        TotalsResource totals,
        RentIndicatorsResource rentIndicators,
        BonusResource bonus,
        List<PaymentResource> payments
) {
}
