package com.acme.finanzasbackend.housingFinance.interfaces.rest.transform;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.CreateHousingResource;

public class CreateHousingCommandFromResourceAssembler {
    public static CreateHousingCommand toCommandFromResource(CreateHousingResource resource) {
        return new CreateHousingCommand(
                resource.realStateCompanyId(),
                resource.title(),
                resource.description(),
                resource.province(),
                resource.district(),
                resource.address(),
                resource.department(),
                resource.area(),
                resource.roomQuantity(),
                resource.salePrice(),
                resource.housingState(),
                resource.housingCategory(),
                resource.currencyId()
        );
    }
}
