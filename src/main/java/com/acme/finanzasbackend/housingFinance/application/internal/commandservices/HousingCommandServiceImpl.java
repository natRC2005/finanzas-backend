package com.acme.finanzasbackend.housingFinance.application.internal.commandservices;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.services.HousingCommandService;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.HousingRepository;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class HousingCommandServiceImpl implements HousingCommandService {
    private final HousingRepository housingRepository;
    private final CurrencyRepository currencyRepository;

    public HousingCommandServiceImpl(HousingRepository housingRepository, CurrencyRepository currencyRepository) {
        this.housingRepository = housingRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Long handle(CreateHousingCommand command) {
        if (housingRepository.existsByTitle(command.title()))
            throw new IllegalArgumentException("Housing already exists");
        var currency = currencyRepository.getById(command.currencyId());
        var housing = new Housing(command, currency);

        try {
            housingRepository.save(housing);
        }  catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return housing.getId();
    }
}
