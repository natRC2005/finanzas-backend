package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetFinanceEntityByIdQuery;

import java.util.Optional;

public interface HousingQueryService {
    Optional<FinanceEntity> handle(GetFinanceEntityByIdQuery query);
}
