package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateCreditApplicationResource;

public class CreateCreditApplicationCommandFromResourceAssembler {

    public static CreateCreditApplicationCommand toCommandFromResource(CreateCreditApplicationResource resource) {
        return new CreateCreditApplicationCommand(
                resource.realStateCompanyId(),
                resource.startDate(),
                resource.clientId(),
                resource.housingId(),
                resource.currencyId(),
                resource.financialEntityId(),
                resource.interestRateType(),
                resource.interestRatePeriod(),
                resource.interestRatePercentage(),
                resource.interestRateNominalCapitalization(),
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
