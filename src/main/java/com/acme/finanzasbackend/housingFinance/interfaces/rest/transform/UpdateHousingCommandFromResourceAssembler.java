package com.acme.finanzasbackend.housingFinance.interfaces.rest.transform;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.UpdateHousingCommand;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.UpdateHousingResource;

public class UpdateHousingCommandFromResourceAssembler {
    public static UpdateHousingCommand toCommandFromResource(Long id, UpdateHousingResource resource) {
        return new UpdateHousingCommand(
                id,
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
