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
                resource.isBonusRequired(),
                resource.gracePeriodType(),
                resource.gracePeriodMonths(),
                resource.notaryCost(),
                resource.registryCost(),
                resource.appraisal(),
                resource.studyCommission(),
                resource.activationCommission(),
                resource.periodicCommission(),
                resource.shippingCosts(),
                resource.administrationExpenses(),
                resource.lifeInsurance(),
                resource.riskInsurance(),
                resource.monthsPaymentTerm(),
                resource.downPaymentPercentage(),
                resource.hasCreditHistory()
        );
    }
}

// UPDATE -> You should ask for years and convert to months at monthsPaymentTerm