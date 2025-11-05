package com.acme.finanzasbackend.creditSimulation.domain.services;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetCreditApplicationByIdQuery;

import java.util.Optional;

public interface CreditApplicationQueryService {
    Optional<CreditApplication> handle(GetCreditApplicationByIdQuery query);
}
