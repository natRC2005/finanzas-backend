package com.acme.finanzasbackend.housingFinance.application.internal.queryservices;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.EvaluateFinanceEntityCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllFinanceEntitiesQuery;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetFinanceEntityByIdQuery;
import com.acme.finanzasbackend.housingFinance.domain.services.FinanceEntityQueryService;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.FinanceEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceEntityQueryServiceImpl implements FinanceEntityQueryService {
    private final FinanceEntityRepository financeEntityRepository;

    public FinanceEntityQueryServiceImpl(FinanceEntityRepository financeEntityRepository) {
        this.financeEntityRepository = financeEntityRepository;
    }

    @Override
    public List<FinanceEntity> handle(GetAllFinanceEntitiesQuery query) {
        return financeEntityRepository.findAll();
    }

    @Override
    public Optional<FinanceEntity> handle(GetFinanceEntityByIdQuery query) {
        return financeEntityRepository.findById(query.id());
    }
}
