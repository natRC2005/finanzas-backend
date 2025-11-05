package com.acme.finanzasbackend.creditSimulation.application.internal.queryservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetBonusByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.BonusQueryService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.BonusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BonusQueryServiceImpl implements BonusQueryService {
    private final BonusRepository bonusRepository;

    public BonusQueryServiceImpl(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @Override
    public Optional<Bonus> handle(GetBonusByIdQuery query) {
        return bonusRepository.findById(query.id());
    }
}
