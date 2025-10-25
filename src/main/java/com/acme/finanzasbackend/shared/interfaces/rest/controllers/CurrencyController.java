package com.acme.finanzasbackend.shared.interfaces.rest.controllers;

import com.acme.finanzasbackend.shared.domain.model.queries.GetAllCurrencyQuery;
import com.acme.finanzasbackend.shared.domain.model.queries.GetCurrencyByIdQuery;
import com.acme.finanzasbackend.shared.domain.services.CurrencyQueryService;
import com.acme.finanzasbackend.shared.interfaces.rest.resources.CurrencyResource;
import com.acme.finanzasbackend.shared.interfaces.rest.transform.CurrencyResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/currency", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Currency", description = "Available Currency Endpoints")
public class CurrencyController {
    private final CurrencyQueryService currencyQueryService;

    public CurrencyController(CurrencyQueryService currencyQueryService) {
        this.currencyQueryService = currencyQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all currency", description = "Get all available currency in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency retrieved successfully.")})
    public ResponseEntity<List<CurrencyResource>> getAllCurrency() {
        var getAllCurrencyQuery = new GetAllCurrencyQuery();
        var currency = currencyQueryService.handle(getAllCurrencyQuery);
        var currencyResources = currency.stream()
                .map(CurrencyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(currencyResources);
    }

    @GetMapping("/{currencyId}")
    @Operation(summary = "Get Currency by id", description = "Get Currency by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency found"),
            @ApiResponse(responseCode = "404", description = "Currency not found")})
    public ResponseEntity<CurrencyResource> getCurrencyById(@PathVariable Long currencyId) {
        var getCurrencyByIdQuery = new GetCurrencyByIdQuery(currencyId);
        var currencyItem = currencyQueryService.handle(getCurrencyByIdQuery);
        if (currencyItem.isEmpty()) return ResponseEntity.notFound().build();
        var currencyEntity = currencyItem.get();
        var currencyResource = CurrencyResourceFromEntityAssembler.toResourceFromEntity(currencyEntity);
        return ResponseEntity.ok(currencyResource);
    }
}
