package com.acme.finanzasbackend.clientManagement.domain.services;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;

public interface ClientCommandService {
    Long handle(CreateClientCommand command);
}
