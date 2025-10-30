package com.acme.finanzasbackend.clientManagement.domain.services;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.UpdateClientCommand;

import java.util.Optional;

public interface ClientCommandService {
    Long handle(CreateClientCommand command);
    Optional<Client> handle(UpdateClientCommand command);
}
