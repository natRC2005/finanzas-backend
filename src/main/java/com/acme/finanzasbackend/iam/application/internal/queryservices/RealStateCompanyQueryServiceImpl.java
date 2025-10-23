package com.acme.finanzasbackend.iam.application.internal.queryservices;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.domain.model.queries.GetRealStateCompanyByIdQuery;
import com.acme.finanzasbackend.iam.domain.services.RealStateCompanyQueryService;
import com.acme.finanzasbackend.iam.infrastructure.persistence.jpa.repositories.RealStateCompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RealStateCompanyQueryServiceImpl implements RealStateCompanyQueryService {
    private final RealStateCompanyRepository realStateCompanyRepository;

    public RealStateCompanyQueryServiceImpl(RealStateCompanyRepository realStateCompanyRepository) {
        this.realStateCompanyRepository = realStateCompanyRepository;
    }


    @Override
    public Optional<RealStateCompany> handle(GetRealStateCompanyByIdQuery query) {
        return realStateCompanyRepository.findById(query.id());
    }
}
