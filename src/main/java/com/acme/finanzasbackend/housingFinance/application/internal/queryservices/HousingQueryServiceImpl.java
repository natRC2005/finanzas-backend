package com.acme.finanzasbackend.housingFinance.application.internal.queryservices;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetHousingByIdQuery;
import com.acme.finanzasbackend.housingFinance.domain.services.HousingQueryService;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.HousingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HousingQueryServiceImpl implements HousingQueryService {
    private final HousingRepository housingRepository;

    public HousingQueryServiceImpl(HousingRepository housingRepository) {
        this.housingRepository = housingRepository;
    }

    @Override
    public Optional<Housing> handle(GetHousingByIdQuery query) {
        return housingRepository.findById(query.id());
    }
}
