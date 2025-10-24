package com.acme.finanzasbackend.iam.application.internal.commandservices;

import com.acme.finanzasbackend.iam.application.internal.outboundingservices.hashing.HashingService;
import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import com.acme.finanzasbackend.iam.domain.model.commands.SignInCommand;
import com.acme.finanzasbackend.iam.domain.model.commands.SignUpCommand;
import com.acme.finanzasbackend.iam.domain.services.RealStateCompanyCommandService;
import com.acme.finanzasbackend.iam.infrastructure.persistence.jpa.repositories.RealStateCompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RealStateCompanyCommandServiceImpl implements RealStateCompanyCommandService {
    private final RealStateCompanyRepository realStateCompanyRepository;
    private final HashingService hashingService;

    public RealStateCompanyCommandServiceImpl(RealStateCompanyRepository realStateCompanyRepository, HashingService hashingService) {
        this.realStateCompanyRepository = realStateCompanyRepository;
        this.hashingService = hashingService;
    }

    @Override
    public Long handle(SignUpCommand command) {
        if (realStateCompanyRepository.existsByCompanyName(command.companyName()))
            throw new IllegalArgumentException("Company name already exists");
        if (realStateCompanyRepository.existsByCompanyEmail(command.companyEmail()))
            throw new IllegalArgumentException("Company email already exists");
        if (realStateCompanyRepository.existsByRuc(command.ruc()))
            throw new IllegalArgumentException("RUC already in use");
        if (realStateCompanyRepository.existsByUsername(command.username()))
            throw new IllegalArgumentException("Username already exists");
        var realStateCompany = new RealStateCompany(command, hashingService.encode(command.password()));
        try {
            realStateCompanyRepository.save(realStateCompany);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return realStateCompany.getId();
    }

    @Override
    public Optional<RealStateCompany> handle(SignInCommand command) {
        RealStateCompany user = realStateCompanyRepository.findByUsername(command.username())
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));
        if (!hashingService.matches(command.password(), user.getPassword()))
            throw new IllegalArgumentException("Wrong password");
        return Optional.of(user);
    }
}
