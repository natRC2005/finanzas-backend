package com.acme.finanzasbackend.creditSimulation.application.internal.queryservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetGracePeriodByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.GracePeriodQueryService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.GracePeriodRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GracePeriodQueryServiceImpl implements GracePeriodQueryService {
    private final GracePeriodRepository gracePeriodRepository;

    public GracePeriodQueryServiceImpl(GracePeriodRepository gracePeriodRepository) {
        this.gracePeriodRepository = gracePeriodRepository;
    }

    @Override
    public Optional<GracePeriod> handle(GetGracePeriodByIdQuery query) {
        return gracePeriodRepository.findById(query.id());
    }
}
