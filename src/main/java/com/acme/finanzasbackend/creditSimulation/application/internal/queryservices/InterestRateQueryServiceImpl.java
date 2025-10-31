package com.acme.finanzasbackend.creditSimulation.application.internal.queryservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetInterestRateByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.InterestRateQueryService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.InterestRateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InterestRateQueryServiceImpl implements InterestRateQueryService {
    private final InterestRateRepository interestRateRepository;

    public InterestRateQueryServiceImpl(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }

    @Override
    public Optional<InterestRate> handle(GetInterestRateByIdQuery query) {
        return interestRateRepository.findById(query.id());
    }
}
