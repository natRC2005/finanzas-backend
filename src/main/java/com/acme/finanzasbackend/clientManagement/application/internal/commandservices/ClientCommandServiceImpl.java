package com.acme.finanzasbackend.clientManagement.application.internal.commandservices;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientCommandService;
import com.acme.finanzasbackend.clientManagement.infrastructure.persistence.jpa.repositories.ClientRepository;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientCommandServiceImpl implements ClientCommandService {
    private final ClientRepository clientRepository;
    private final CurrencyRepository currencyRepository;

    public ClientCommandServiceImpl(ClientRepository clientRepository, CurrencyRepository currencyRepository) {
        this.clientRepository = clientRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Long handle(CreateClientCommand command) {
        if (clientRepository.existsByFirstnameAndLastname(command.firstname(), command.lastname()) ||
            clientRepository.existsByDni(command.dni())) {
            throw new IllegalArgumentException("This client already exists");
        }
        var currency = currencyRepository.getById(command.currencyId());
        var client = new Client(command, currency);

        try {
            clientRepository.save(client);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return client.getId();
    }
}
