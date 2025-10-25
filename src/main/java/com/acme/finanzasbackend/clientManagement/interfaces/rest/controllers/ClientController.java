package com.acme.finanzasbackend.clientManagement.interfaces.rest.controllers;

import com.acme.finanzasbackend.clientManagement.domain.model.queries.GetClientByIdQuery;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientCommandService;
import com.acme.finanzasbackend.clientManagement.domain.services.ClientQueryService;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.ClientResource;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.CreateClientResource;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.transform.ClientResourceFromEntityAssembler;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.transform.CreateClientCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/items")
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
}
