package com.acme.finanzasbackend.clientManagement.interfaces.rest.controllers;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.DeleteClientCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.commands.ExchangeSalaryCurrencyCommand;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetAllClientsQuery;
import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetClientByIdQuery;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientCommandService;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientQueryService;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.ClientResource;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.CreateClientResource;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.UpdateClientResource;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.transform.ClientResourceFromEntityAssembler;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.transform.CreateClientCommandFromResourceAssembler;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.transform.UpdateClientCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/clients", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Clients", description = "Available Clients Endpoints")
public class ClientController {
    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @PostMapping("/clients")
    @Operation(summary = "Create a new Client", description = "Create a new Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Client not found")})
    public ResponseEntity<ClientResource> createClient(@RequestBody CreateClientResource resource) {
        var createCommand = CreateClientCommandFromResourceAssembler.toCommandFromResource(resource);
        var clientId = clientCommandService.handle(createCommand);
        if (clientId == null || clientId == 0L) return ResponseEntity.badRequest().build();
        var getClientByIdQuery = new GetClientByIdQuery(clientId);
        var clientItem = clientQueryService.handle(getClientByIdQuery);
        if (clientItem.isEmpty()) return ResponseEntity.notFound().build();
        var clientEntity = clientItem.get();
        var clientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(clientEntity);
        return new ResponseEntity<>(clientResource, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Get Client by id", description = "Get Client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")})
    public ResponseEntity<ClientResource> getClientById(@PathVariable Long clientId) {
        var getClientByIdQuery = new GetClientByIdQuery(clientId);
        var client = clientQueryService.handle(getClientByIdQuery);
        if (client.isEmpty()) return ResponseEntity.notFound().build();
        var clientEntity = client.get();
        var clientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(clientEntity);
        return ResponseEntity.ok(clientResource);
    }

    @GetMapping("/{realStateCompanyId}/clients")
    @Operation(summary = "Get all clients", description = "Get all available clients in the system by realStateCompanyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully.")})
    public ResponseEntity<List<ClientResource>> getAllCurrency(@PathVariable Long realStateCompanyId) {
        var getAllClientsQuery = new GetAllClientsQuery(realStateCompanyId);
        var clients = clientQueryService.handle(getAllClientsQuery);
        var clientResources = clients.stream()
                .map(ClientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(clientResources);
    }

    @PutMapping("/{clientId}")
    @Operation(summary = "Update Client", description = "Updates all editable fields of a Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResource> updateClient(@PathVariable Long clientId, @RequestBody UpdateClientResource resource) {
        var updateClientCommand = UpdateClientCommandFromResourceAssembler.toCommandFromResource(clientId, resource);
        var updatedClient = clientCommandService.handle(updateClientCommand);
        if (updatedClient.isEmpty()) return ResponseEntity.notFound().build();
        var updatedClientEntity = updatedClient.get();
        var updatedClientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(updatedClientEntity);
        return ResponseEntity.ok(updatedClientResource);
    }

    @DeleteMapping("/{clientId}")
    @Operation(summary = "Delete Client", description = "Delete Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Client not found.")})
    public ResponseEntity<ClientResource> deleteClient(@PathVariable Long clientId) {
        DeleteClientCommand command = new DeleteClientCommand(clientId);
        var client = clientCommandService.handle(command);
        if (client.isEmpty()) return ResponseEntity.notFound().build();
        var clientEntity = client.get();
        var clientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(clientEntity);
        return ResponseEntity.ok(clientResource);
    }

    @PatchMapping("/{clientId}")
    @Operation(summary = "Exchange Client salary currency", description = "Exchange Client salary currency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully."),
            @ApiResponse(responseCode = "404", description = "Client not found.")})
    public ResponseEntity<ClientResource> exchangeClientSalary(@PathVariable Long clientId) {
        ExchangeSalaryCurrencyCommand command = new ExchangeSalaryCurrencyCommand(clientId);
        var client = clientCommandService.handle(command);
        if (client.isEmpty()) return ResponseEntity.notFound().build();
        var clientEntity = client.get();
        var clientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(clientEntity);
        return ResponseEntity.ok(clientResource);
    }
}
