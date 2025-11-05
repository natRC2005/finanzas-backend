package com.acme.finanzasbackend.creditSimulation.application.internal.queryservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetCreditApplicationByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.CreditApplicationQueryService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.CreditApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditApplicationQueryServiceImpl implements CreditApplicationQueryService {
    private final CreditApplicationRepository creditApplicationRepository;

    public CreditApplicationQueryServiceImpl(CreditApplicationRepository creditApplicationRepository) {
        this.creditApplicationRepository = creditApplicationRepository;
    }

    @Override
    public Optional<CreditApplication> handle(GetCreditApplicationByIdQuery query) {
        return creditApplicationRepository.findById(query.id());
    }
}
