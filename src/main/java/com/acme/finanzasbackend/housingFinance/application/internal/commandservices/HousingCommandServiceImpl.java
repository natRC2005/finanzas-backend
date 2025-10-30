package com.acme.finanzasbackend.housingFinance.application.internal.commandservices;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.DeleteHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.ExchangeSalePriceCurrencyCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.UpdateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.services.HousingCommandService;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.HousingRepository;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Housing> handle(UpdateHousingCommand command) {
        Housing housing = housingRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Housing not found"));
        var currency = currencyRepository.getById(command.currencyId());
        housing.modifyHousing(command, currency);
        try {
            housingRepository.save(housing);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return Optional.of(housing);
    }

    @Override
    public Optional<Housing> handle(DeleteHousingCommand command) {
        Housing housing = housingRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Housing not found"));
        housingRepository.delete(housing);
        return Optional.of(housing);
    }

    @Override
    public Optional<Housing> handle(ExchangeSalePriceCurrencyCommand command) {
        Housing housing = housingRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Housing not found"));
        Long otherCurrency = 1L;
        if (housing.getCurrency().getId() == 1) otherCurrency = 2L;
        var currency = currencyRepository.getById(otherCurrency);
        housing.exchangeSalePriceCurrency(currency);
        try {
            housingRepository.save(housing);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return Optional.of(housing);
    }
}
