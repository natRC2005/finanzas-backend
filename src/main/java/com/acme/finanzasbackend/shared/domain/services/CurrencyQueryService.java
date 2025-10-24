package com.acme.finanzasbackend.shared.domain.services;

import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.queries.GetAllCurrencyQuery;

import java.util.List;

public interface CurrencyQueryService {
    List<Currency> handle(GetAllCurrencyQuery query);
}
