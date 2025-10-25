package com.acme.finanzasbackend.housingFinance.interfaces.rest.controllers;

import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllFinanceEntitiesQuery;
import com.acme.finanzasbackend.housingFinance.domain.services.FinanceEntityQueryService;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.FinanceEntityResource;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.transform.FinanceEntityResourceFromEntityAssembler;
import com.acme.finanzasbackend.shared.domain.model.queries.GetAllCurrencyQuery;
import com.acme.finanzasbackend.shared.interfaces.rest.resources.CurrencyResource;
import com.acme.finanzasbackend.shared.interfaces.rest.transform.CurrencyResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/finance-entities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Finance Entities", description = "Available Finance Entities Endpoints")
public class FinanceEntityController {
    private final FinanceEntityQueryService financeEntityQueryService;

    public FinanceEntityController(FinanceEntityQueryService financeEntityQueryService) {
        this.financeEntityQueryService = financeEntityQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all finance entities", description = "Get all available finance entities in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Finance Entities retrieved successfully.")})
    public ResponseEntity<List<FinanceEntityResource>> getAllFinanceEntities() {
        var getAllFinanceEntityQuery = new GetAllFinanceEntitiesQuery();
        var financeEntities = financeEntityQueryService.handle(getAllFinanceEntityQuery);
        var financeEntitiesResources = financeEntities.stream()
                .map(FinanceEntityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(financeEntitiesResources);
    }
}
