package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllFinanceEntitiesQuery;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetFinanceEntityByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FinanceEntityQueryService {
    List<FinanceEntity> handle(GetAllFinanceEntitiesQuery query);
    Optional<FinanceEntity> handle(GetFinanceEntityByIdQuery query);
}
