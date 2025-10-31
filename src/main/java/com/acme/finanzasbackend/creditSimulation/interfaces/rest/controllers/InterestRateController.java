package com.acme.finanzasbackend.creditSimulation.interfaces.rest.controllers;

import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetInterestRateByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.InterestRateCommandService;
import com.acme.finanzasbackend.creditSimulation.domain.services.InterestRateQueryService;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateInterestRateResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.InterestRateResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.CreateInterestRateCommandFromResourceAssembler;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.InterestRateResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/interest-rates", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Interest Rates", description = "Available Interest Rates Endpoints")
public class InterestRateController {
    private final InterestRateCommandService interestRateCommandService;
    private final InterestRateQueryService interestRateQueryService;

    public InterestRateController(InterestRateCommandService interestRateCommandService, InterestRateQueryService interestRateQueryService) {
        this.interestRateCommandService = interestRateCommandService;
        this.interestRateQueryService = interestRateQueryService;
    }

    @PostMapping("/interest-rates")
    @Operation(summary = "Create a new Interest Rate", description = "Create a new Interest Rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interest Rate created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Interest Rate not found")})
    public ResponseEntity<InterestRateResource> createInterestRate(@RequestBody CreateInterestRateResource resource) {
        var createCommand = CreateInterestRateCommandFromResourceAssembler.toCommandFromResource(resource);
        var interestRateId = interestRateCommandService.handle(createCommand);
        if (interestRateId == null || interestRateId == 0L) return ResponseEntity.badRequest().build();
        var getInterestRateByIdQuery = new GetInterestRateByIdQuery(interestRateId);
        var interestRateItem = interestRateQueryService.handle(getInterestRateByIdQuery);
        if (interestRateItem.isEmpty()) return ResponseEntity.notFound().build();
        var interestRateEntity = interestRateItem.get();
        var interestRateResource = InterestRateResourceFromEntityAssembler.toResourceFromEntity(interestRateEntity);
        return new ResponseEntity<>(interestRateResource, HttpStatus.CREATED);
    }

    @GetMapping("/{interestRateId}")
    @Operation(summary = "Get Interest Rate by id", description = "Get Interest Rate by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interest Rate found"),
            @ApiResponse(responseCode = "404", description = "Interest Rate not found")})
    public ResponseEntity<InterestRateResource> getInterestRateById(@PathVariable Long interestRateId) {
        var getInterestRateByIdQuery = new GetInterestRateByIdQuery(interestRateId);
        var interestRate = interestRateQueryService.handle(getInterestRateByIdQuery);
        if (interestRate.isEmpty()) return ResponseEntity.notFound().build();
        var interestRateEntity = interestRate.get();
        var interestRateResource = InterestRateResourceFromEntityAssembler.toResourceFromEntity(interestRateEntity);
        return ResponseEntity.ok(interestRateResource);
    }
}
