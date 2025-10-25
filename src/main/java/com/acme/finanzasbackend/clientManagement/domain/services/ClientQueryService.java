package com.acme.finanzasbackend.clientManagement.domain.services;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetAllClientsQuery;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetClientByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ClientQueryService {
    Optional<Client> handle(GetClientByIdQuery query);
    List<Client> handle(GetAllClientsQuery query);
}
