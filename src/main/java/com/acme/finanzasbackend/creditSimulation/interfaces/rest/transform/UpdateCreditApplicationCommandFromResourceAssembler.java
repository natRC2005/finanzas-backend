package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.UpdateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.UpdateCreditApplicationResource;

public class UpdateCreditApplicationCommandFromResourceAssembler {
    public static UpdateCreditApplicationCommand toCommandFromResource(Long id, UpdateCreditApplicationResource resource) {
        return new UpdateCreditApplicationCommand(
                id,
                resource.startDate(),
                resource.clientId(),
                resource.housingId(),
                resource.currencyId(),
                resource.financialEntityId(),
                resource.interestRateType(),
                resource.interestRatePeriod(),
                resource.interestRatePercentage(),
                resource.interestRateNominalCapitalization(),
                resource.cokType(),
                resource.cokPeriod(),
                resource.cokPercentage(),
                resource.cokNominalCapitalization(),
                resource.isBonusRequired(),
                resource.gracePeriodType(),
                resource.gracePeriodMonths(),
                resource.notaryCost(),
                resource.registryCost(),
                resource.appraisal(),
                resource.studyCommission(),
                resource.activationCommission(),
                resource.professionalFeesCost(),
                resource.documentationFee(),
                resource.periodicCommission(),
                resource.shippingCosts(),
                resource.administrationExpenses(),
                resource.lifeInsurance(),
                resource.riskInsurance(),
                resource.monthlyStatementDelivery(),
                resource.yearsPaymentTerm(),
                resource.downPaymentPercentage(),
                resource.hasCreditHistory()
        );
    }
}
