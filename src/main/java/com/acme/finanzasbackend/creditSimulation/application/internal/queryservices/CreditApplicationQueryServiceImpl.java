package com.acme.finanzasbackend.creditSimulation.application.internal.queryservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetAllCreditApplicationsQuery;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetCreditApplicationByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.CreditApplicationQueryService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.CreditApplicationRepository;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<CreditApplication> handle(GetAllCreditApplicationsQuery query) {
        return creditApplicationRepository.findAllByRealStateCompanyId(new RealStateCompanyId(query.realStateCompanyId()));
    }
}
