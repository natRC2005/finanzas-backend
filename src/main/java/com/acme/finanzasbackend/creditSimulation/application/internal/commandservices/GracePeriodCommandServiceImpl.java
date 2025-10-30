package com.acme.finanzasbackend.creditSimulation.application.internal.commandservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateGracePeriodCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.services.GracePeriodCommandService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.GracePeriodRepository;
import org.springframework.stereotype.Service;

@Service
public class GracePeriodCommandServiceImpl implements GracePeriodCommandService {
    private final GracePeriodRepository gracePeriodRepository;

    public GracePeriodCommandServiceImpl(GracePeriodRepository gracePeriodRepository) {
        this.gracePeriodRepository = gracePeriodRepository;
    }

    @Override
    public Long handle(CreateGracePeriodCommand command) {
        var gracePeriod = new GracePeriod(command);
        try {
            gracePeriodRepository.save(gracePeriod);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return gracePeriod.getId();
    }
}
