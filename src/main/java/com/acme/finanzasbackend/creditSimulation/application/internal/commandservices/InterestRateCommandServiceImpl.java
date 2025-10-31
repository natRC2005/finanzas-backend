package com.acme.finanzasbackend.creditSimulation.application.internal.commandservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateInterestRateCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.services.InterestRateCommandService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.InterestRateRepository;
import org.springframework.stereotype.Service;

@Service
public class InterestRateCommandServiceImpl implements InterestRateCommandService {
    private final InterestRateRepository interestRateRepository;

    public InterestRateCommandServiceImpl(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }

    @Override
    public Long handle(CreateInterestRateCommand command) {
        var interestRate = new InterestRate(command);
        try {
            interestRateRepository.save(interestRate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return interestRate.getId();
    }
}
