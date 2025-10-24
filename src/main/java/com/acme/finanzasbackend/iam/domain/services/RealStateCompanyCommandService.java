package com.acme.finanzasbackend.iam.domain.services;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.domain.model.commands.SignInCommand;
import com.acme.finanzasbackend.iam.domain.model.commands.SignUpCommand;

import java.util.Optional;

public interface RealStateCompanyCommandService {
    Long handle(SignUpCommand command);
    Optional<RealStateCompany> handle(SignInCommand command);
}
