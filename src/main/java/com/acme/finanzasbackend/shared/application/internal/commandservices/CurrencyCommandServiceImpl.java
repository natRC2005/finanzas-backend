package com.acme.finanzasbackend.shared.application.internal.commandservices;

import com.acme.finanzasbackend.shared.domain.model.commands.SeedCurrencyCommand;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.services.CurrencyCommandService;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyCommandServiceImpl implements CurrencyCommandService {
    private final CurrencyRepository currencyRepository;

    public CurrencyCommandServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void handle(SeedCurrencyCommand command) {
        var defaultCurrency = List.of(
                new Currency("PEN", "soles", "S/ "),
                new Currency("USD", "dollars", "$ ")
        );
        defaultCurrency.forEach(currency -> {
            if (!currencyRepository.existsByName(currency.getName())) {
                currencyRepository.save(currency);
            }
        });
    }
}
