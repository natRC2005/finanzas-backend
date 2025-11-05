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
                resource.interestRateCapitalization() != null
                        ? resource.interestRateCapitalization()
                        : null,
                resource.interestRatePercentage(),
                resource.isBonusRequired(),
                resource.gracePeriodType(),
                resource.gracePeriodMonths(),
                resource.monthsPaymentTerm(),
                resource.downPaymentPercentage(),
                resource.monthlyLifeInsuranceRate(),
                resource.monthlyHousingInsuranceRate(),
                resource.hasCreditHistory()
        );
    }
}