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
        String interestRatePeriod,
        Double interestRatePercentage,
        Boolean isBonusRequired,
        String gracePeriodType,
        Integer gracePeriodMonths,
        Double notaryCost,
        Double registryCost,
        Double appraisal, // tasacion
        Double studyCommission,
        Double activationCommission,
        Double periodicCommission,
        Double shippingCosts, // portes
        Double administrationExpenses,
        Double lifeInsurance, // desgravamen
        Double riskInsurance,
        Double yearsPaymentTerm,
        Double downPaymentPercentage,
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
        if (gracePeriodMonths == null || gracePeriodMonths < 0 || gracePeriodMonths > 6) {
            throw new IllegalArgumentException("gracePeriodMonths must not be less than 0 or greater than 6");
        }
        if (notaryCost == null || notaryCost < 0) {
            throw new IllegalArgumentException("notaryCost cannot be negative");
        }
        if (registryCost == null || registryCost < 0) {
            throw new IllegalArgumentException("registryCost cannot be negative");
        }
        if (appraisal == null || appraisal < 0) {
            throw new IllegalArgumentException("appraisal cannot be negative");
        }
        if (studyCommission == null || studyCommission < 0) {
            throw new IllegalArgumentException("studyCommission cannot be negative");
        }
        if (activationCommission == null || activationCommission < 0) {
            throw new IllegalArgumentException("activationCommission cannot be negative");
        }
        if (periodicCommission == null || periodicCommission < 0) {
            throw new IllegalArgumentException("periodicCommission cannot be negative");
        }
        if (shippingCosts == null || shippingCosts < 0) {
            throw new IllegalArgumentException("shippingCosts cannot be negative");
        }
        if (administrationExpenses == null || administrationExpenses < 0) {
            throw new IllegalArgumentException("administrationExpenses cannot be negative");
        }
        if (lifeInsurance == null || lifeInsurance < 0) {
            throw new IllegalArgumentException("lifeInsurance cannot be negative");
        }
        if (riskInsurance == null || riskInsurance < 0) {
            throw new IllegalArgumentException("riskInsurance cannot be negative");
        }
        if (yearsPaymentTerm == null || yearsPaymentTerm < 1) {
            throw new IllegalArgumentException("yearsPaymentTerm must not be less than 1");
        }
        if (downPaymentPercentage == null || downPaymentPercentage < 0) {
            throw new IllegalArgumentException("downPaymentPercentage cannot be negative");
        }
        if (hasCreditHistory == null) {
            throw new IllegalArgumentException("hasCreditHistory cannot be null");
        }
    }
}
