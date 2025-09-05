package com.acme.finanzasbackend.iam.domain.services;

import com.acme.finanzasbackend.iam.domain.model.commands.SignUpCommand;

public interface RealStateCompanyCommandService {
    Long handle(SignUpCommand command);
}
