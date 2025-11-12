package com.acme.finanzasbackend.housingFinance.interfaces.rest.transform;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.HousingResource;

public class HousingResourceFromEntityAssembler {
    public static HousingResource toResourceFromEntity(Housing entity) {
        return new HousingResource(
                entity.getId(),
                entity.getRealStateCompanyId().realStateCompanyId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getProvince().toString(),
                entity.getDistrict(),
                entity.getAddress(),
                entity.getDepartment(),
                entity.getArea(),
                entity.getRoomQuantity(),
                entity.getSalePrice(),
                entity.getHousingState().toString(),
                entity.getHousingCategory().toString(),
                entity.getCurrency().getSymbol()
        );
    }
}
