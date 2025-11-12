package com.acme.finanzasbackend.housingFinance.interfaces.rest.resources;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;

public record HousingResource(
        Long id,
        Long realStateCompanyId,
        String title,
        String description,
        String province,
        String district,
        String address,
        String department,
        Double area,
        Integer roomQuantity,
        Double salePrice,
        String housingState,
        String housingCategory,
        String currencySymbol
) {
}
