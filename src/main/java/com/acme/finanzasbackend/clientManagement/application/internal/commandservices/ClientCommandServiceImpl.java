package com.acme.finanzasbackend.clientManagement.application.internal.commandservices;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.DeleteClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.ExchangeSalaryCurrencyCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.UpdateClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientCommandService;
import com.acme.finanzasbackend.clientManagement.infrastructure.persistence.jpa.repositories.ClientRepository;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Client> handle(UpdateClientCommand command) {
        Client client = clientRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        var currency = currencyRepository.getById(command.currencyId());
        client.modifyClient(command, currency);
        try {
            clientRepository.save(client);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return Optional.of(client);
    }

    @Override
    public Optional<Client> handle(DeleteClientCommand command) {
        Client client = clientRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        clientRepository.delete(client);
        return Optional.of(client);
    }

    @Override
    public Optional<Client> handle(ExchangeSalaryCurrencyCommand command) {
        Client client = clientRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Long otherCurrency = 1L;
        if (client.getCurrency().getId() == 1) otherCurrency = 2L;
        var currency = currencyRepository.getById(otherCurrency);
        client.exchangeSalaryCurrency(currency);
        try {
            clientRepository.save(client);
        }  catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return Optional.of(client);
    }
}
