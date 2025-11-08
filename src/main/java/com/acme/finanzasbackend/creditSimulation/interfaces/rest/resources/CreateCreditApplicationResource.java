package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCreditApplicationResource(
        Long realStateCompanyId,
        LocalDate startDate,
        Long clientId,
        Long housingId,
        Long currencyId,
        Long financialEntityId,
        String interestRateType,
        String interestRatePeriod,
        Double interestRatePercentage,
        Boolean isBonusRequired,
        String gracePeriodType,
        Integer gracePeriodMonths,
        Double monthsPaymentTerm,
        Double downPaymentPercentage,
        Double monthlyLifeInsuranceRate,
        Double monthlyHousingInsuranceRate,
        Boolean hasCreditHistory
) {
    public CreateCreditApplicationResource {
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
        if (interestRatePeriod == null) {
            throw new IllegalArgumentException("interestRatePeriod cannot be null");
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
        if (monthlyLifeInsuranceRate == null || monthlyLifeInsuranceRate < 0) {
            throw new IllegalArgumentException("monthlyLifeInsuranceRate cannot be negative");
        }
        if (monthlyHousingInsuranceRate == null || monthlyHousingInsuranceRate < 0) {
            throw new IllegalArgumentException("monthlyHousingInsuranceRate cannot be negative");
        }
        if (hasCreditHistory == null) {
            throw new IllegalArgumentException("hasCreditHistory cannot be null");
        }
    }
}
