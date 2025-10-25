package com.acme.finanzasbackend.shared.domain.services;

import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.queries.GetAllCurrencyQuery;
import com.acme.finanzasbackend.shared.domain.model.queries.GetCurrencyByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CurrencyQueryService {
    List<Currency> handle(GetAllCurrencyQuery query);
    Optional<Currency> handle(GetCurrencyByIdQuery query);
}
