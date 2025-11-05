package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetBonusByIdQuery;

import java.util.Optional;

public interface BonusQueryService {
    Optional<Bonus> handle(GetBonusByIdQuery query);
}
