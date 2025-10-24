package com.acme.finanzasbackend.shared.interfaces.rest.transform;

import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.interfaces.rest.resources.CurrencyResource;

public class CurrencyResourceFromEntityAssembler {
    public static CurrencyResource toResourceFromEntity(Currency entity) {
        return new CurrencyResource(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getSymbol()
        );
    }
}
