package com.acme.finanzasbackend.clientManagement.application.internal.queryservices;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetAllClientsQuery;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetClientByIdQuery;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientQueryService;
import com.acme.finanzasbackend.clientManagement.infrastructure.persistence.jpa.repositories.ClientRepository;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientQueryServiceImpl implements ClientQueryService {
    private final ClientRepository clientRepository;

    public  ClientQueryServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> handle(GetClientByIdQuery query) {
        return clientRepository.findById(query.id());
    }

    @Override
    public List<Client> handle(GetAllClientsQuery query) {
        return clientRepository.findAllByRealStateCompanyId(new RealStateCompanyId(query.realStateCompanyId()));
    }
}
