package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateBonusCommand;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateBonusResource;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;

public class CreateBonusCommandFromResourceAssembler {
    public static CreateBonusCommand toCommandFromResource(CreateBonusResource resource){
        return new CreateBonusCommand(
                resource.isRequired(),
                HousingState.valueOf(resource.housingState()),
                resource.housingSalePrice()
        );
    }
}
