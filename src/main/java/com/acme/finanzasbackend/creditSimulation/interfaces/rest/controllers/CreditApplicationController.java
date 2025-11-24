package com.acme.finanzasbackend.creditSimulation.interfaces.rest.controllers;

import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetAllCreditApplicationsQuery;
import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetCreditApplicationByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.CreditApplicationCommandService;
import com.acme.finanzasbackend.creditSimulation.domain.services.CreditApplicationQueryService;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateCreditApplicationResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreditApplicationResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.CreateCreditApplicationCommandFromResourceAssembler;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.CreditApplicationResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/credit-applications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Credit Applications", description = "Available Credit Applications Endpoints")
public class CreditApplicationController {
    private final CreditApplicationCommandService creditApplicationCommandService;
    private final CreditApplicationQueryService creditApplicationQueryService;

    public CreditApplicationController(CreditApplicationCommandService creditApplicationCommandService, CreditApplicationQueryService creditApplicationQueryService) {
        this.creditApplicationCommandService = creditApplicationCommandService;
        this.creditApplicationQueryService = creditApplicationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Credit Application", description = "Creates a new Credit Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit Application created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Related entities not found")
    })
    public ResponseEntity<CreditApplicationResource> createCreditApplication(@RequestBody CreateCreditApplicationResource resource) {
        var createCommand = CreateCreditApplicationCommandFromResourceAssembler.toCommandFromResource(resource);
        var creditApplicationId = creditApplicationCommandService.handle(createCommand);
        if (creditApplicationId == null || creditApplicationId == 0L) return ResponseEntity.badRequest().build();
        var getCreditApplicationByIdQuery = new GetCreditApplicationByIdQuery(creditApplicationId);
        var creditApplication = creditApplicationQueryService.handle(getCreditApplicationByIdQuery);
        if (creditApplication.isEmpty()) return ResponseEntity.notFound().build();
        var creditApplicationEntity = creditApplication.get();
        var creditApplicationResource = CreditApplicationResourceFromEntityAssembler.toResourceFromEntity(creditApplicationEntity);
        return new ResponseEntity<>(creditApplicationResource, HttpStatus.CREATED);
    }

    @GetMapping("/{creditApplicationId}")
    @Operation(summary = "Get Credit Application by id", description = "Retrieve a Credit Application by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit Application found"),
            @ApiResponse(responseCode = "404", description = "Credit Application not found")
    })
    public ResponseEntity<CreditApplicationResource> getCreditApplicationById(
            @PathVariable Long creditApplicationId) {
        var getCreditApplicationByIdQuery = new GetCreditApplicationByIdQuery(creditApplicationId);
        var creditApplication = creditApplicationQueryService.handle(getCreditApplicationByIdQuery);
        if (creditApplication.isEmpty()) return ResponseEntity.notFound().build();
        var creditApplicationEntity = creditApplication.get();
        var creditApplicationResource = CreditApplicationResourceFromEntityAssembler.toResourceFromEntity(creditApplicationEntity);
        return ResponseEntity.ok(creditApplicationResource);
    }

    @GetMapping("/{realStateCompanyId}/creditApplications")
    @Operation(summary = "Get all Credit Applications", description = "Get all available Credit Applications in the system by realStateCompanyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit Applications retrieved successfully.")})
    public ResponseEntity<List<CreditApplicationResource>> getAllCreditApplications(@PathVariable Long realStateCompanyId) {
        var getAllCreditApplicationsQuery = new GetAllCreditApplicationsQuery(realStateCompanyId);
        var creditApplications = creditApplicationQueryService.handle(getAllCreditApplicationsQuery);
        var creditApplicationResources = creditApplications.stream()
                .map(CreditApplicationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(creditApplicationResources);
    }
}