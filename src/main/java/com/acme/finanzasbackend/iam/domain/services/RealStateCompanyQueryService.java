package com.acme.finanzasbackend.iam.domain.services;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.domain.model.queries.GetRealStateCompanyByIdQuery;

import java.util.Optional;

public interface RealStateCompanyQueryService {
    Optional<RealStateCompany> handle(GetRealStateCompanyByIdQuery query);
}
