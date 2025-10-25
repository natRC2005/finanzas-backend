package com.acme.finanzasbackend.housingFinance.domain.services;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllFinanceEntitiesQuery;

import java.util.List;

public interface FinanceEntityQueryService {
    List<FinanceEntity> handle(GetAllFinanceEntitiesQuery query);
}
