package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetInterestRateByIdQuery;

import java.util.Optional;

public interface InterestRateQueryService {
    Optional<InterestRate> handle(GetInterestRateByIdQuery query);
}
