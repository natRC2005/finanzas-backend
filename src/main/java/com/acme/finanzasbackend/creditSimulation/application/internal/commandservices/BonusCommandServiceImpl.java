package com.acme.finanzasbackend.creditSimulation.application.internal.commandservices;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateBonusCommand;
import com.acme.finanzasbackend.creditSimulation.domain.services.BonusCommandService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.BonusRepository;
import org.springframework.stereotype.Service;

@Service
public class BonusCommandServiceImpl implements BonusCommandService {
    private final BonusRepository bonusRepository;

    public BonusCommandServiceImpl(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @Override
    public Long handle(CreateBonusCommand command) {
        var bonus = new Bonus(command);
        try {
            bonusRepository.save(bonus);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return bonus.getId();
    }
}
