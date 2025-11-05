package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

import java.time.LocalDate;

public record CreateCreditApplicationCommand(
        Long realStateCompanyId,
        LocalDate startDate,
        Long clientId,
        Long housingId,
        Long currencyId,
        Long financialEntityId,
        String interestRateType,
        String interestRateCapitalization,
        Double interestRatePercentage,
        Boolean isBonusRequired,
        String gracePeriodType,
        Integer gracePeriodMonths,
        Double monthsPaymentTerm,
        Double downPaymentPercentage,
        Double insuranceFee,
        Boolean hasCreditHistory
) {
    public CreateCreditApplicationCommand {
        if (realStateCompanyId == null) {
            throw new IllegalArgumentException("realStateCompanyId cannot be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        }
        if (clientId == null) {
            throw new IllegalArgumentException("clientId cannot be null");
        }
        if (housingId == null) {
            throw new IllegalArgumentException("housingId cannot be null");
        }
        if (currencyId == null) {
            throw new IllegalArgumentException("currencyId cannot be null");
        }
        if (financialEntityId == null) {
            throw new IllegalArgumentException("financialEntityId cannot be null");
        }
        if (interestRateType == null) {
            throw new IllegalArgumentException("InterestRateType cannot be null");
        }
        if (interestRateType.equals("NOMINAL") && interestRateCapitalization == null) {
            throw new IllegalArgumentException("Capitalization cannot be null");
        }
        if (interestRatePercentage == null || interestRatePercentage < 0) {
            throw new IllegalArgumentException("InterestRatePercentage cannot be negative");
        }
        if (isBonusRequired == null) {
            throw new IllegalArgumentException("isBonusRequired cannot be null");
        }
        if (gracePeriodType == null) {
            throw new IllegalArgumentException("gracePeriodType must not be null");
        }
        if (gracePeriodMonths == null || gracePeriodMonths < 1) {
            throw new IllegalArgumentException("gracePeriodMonths must not be less than 1");
        }
        if (monthsPaymentTerm == null || monthsPaymentTerm < 1) {
            throw new IllegalArgumentException("monthsPaymentTerm must not be less than 1");
        }
        if (downPaymentPercentage == null || downPaymentPercentage < 0) {
            throw new IllegalArgumentException("downPaymentPercentage cannot be negative");
        }
        if (insuranceFee == null || insuranceFee < 0) {
            throw new IllegalArgumentException("insuranceFee cannot be negative");
        }
        if (hasCreditHistory == null) {
            throw new IllegalArgumentException("hasCreditHistory cannot be null");
        }
    }
}
