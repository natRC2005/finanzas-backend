package com.acme.finanzasbackend.shared.application.internal.queryservices;

import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.queries.GetAllCurrencyQuery;
import com.acme.finanzasbackend.shared.domain.model.queries.GetCurrencyByIdQuery;
import com.acme.finanzasbackend.shared.domain.services.CurrencyQueryService;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyQueryServiceImpl implements CurrencyQueryService {
    private final CurrencyRepository currencyRepository;

    public CurrencyQueryServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> handle(GetAllCurrencyQuery query) {
        return currencyRepository.findAll();
    }

    @Override
    public Optional<Currency> handle(GetCurrencyByIdQuery query) {
        return currencyRepository.findById(query.id());
    }
}
