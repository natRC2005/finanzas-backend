package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetGracePeriodByIdQuery;

import java.util.Optional;

public interface GracePeriodQueryService {
    Optional<GracePeriod> handle(GetGracePeriodByIdQuery query);
}
