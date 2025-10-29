package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllHousingQuery;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetHousingByIdQuery;

import java.util.List;
import java.util.Optional;

public interface HousingQueryService {
    Optional<Housing> handle(GetHousingByIdQuery query);
    List<Housing> handle(GetAllHousingQuery query);
}
