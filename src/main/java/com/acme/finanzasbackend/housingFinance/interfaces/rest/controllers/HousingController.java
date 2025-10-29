package com.acme.finanzasbackend.housingFinance.interfaces.rest.controllers;

import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetAllHousingQuery;
import com.acme.finanzasbackend.housingFinance.domain.model.queries.GetHousingByIdQuery;
import com.acme.finanzasbackend.housingFinance.domain.services.HousingCommandService;
import com.acme.finanzasbackend.housingFinance.domain.services.HousingQueryService;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.CreateHousingResource;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.resources.HousingResource;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.transform.CreateHousingCommandFromResourceAssembler;
import com.acme.finanzasbackend.housingFinance.interfaces.rest.transform.HousingResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/housing", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Housing", description = "Available Housing Endpoints")
public class HousingController {
    private final HousingCommandService housingCommandService;
    private final HousingQueryService housingQueryService;

    public HousingController(HousingCommandService housingCommandService, HousingQueryService housingQueryService) {
        this.housingCommandService = housingCommandService;
        this.housingQueryService = housingQueryService;
    }

    @PostMapping("/housing")
    @Operation(summary = "Create a new Housing", description = "Create a new Housing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Housing created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Housing not found")})
    public ResponseEntity<HousingResource> createHousing(@RequestBody CreateHousingResource resource) {
        var createCommand = CreateHousingCommandFromResourceAssembler.toCommandFromResource(resource);
        var housingId = housingCommandService.handle(createCommand);
        if (housingId == null || housingId == 0L) return ResponseEntity.badRequest().build();
        var getHousingByIdQuery = new GetHousingByIdQuery(housingId);
        var housingItem = housingQueryService.handle(getHousingByIdQuery);
        if (housingItem.isEmpty()) return ResponseEntity.notFound().build();
        var housingEntity = housingItem.get();
        var housingResource = HousingResourceFromEntityAssembler.toResourceFromEntity(housingEntity);
        return new ResponseEntity<>(housingResource, HttpStatus.CREATED);
    }

    @GetMapping("/{housingId}")
    @Operation(summary = "Get Housing by id", description = "Get Housing by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Housing found"),
            @ApiResponse(responseCode = "404", description = "Housing not found")})
    public ResponseEntity<HousingResource> getHousingById(@PathVariable Long housingId) {
        var getHousingByIdQuery = new GetHousingByIdQuery(housingId);
        var housing = housingQueryService.handle(getHousingByIdQuery);
        if (housing.isEmpty()) return ResponseEntity.notFound().build();
        var housingEntity = housing.get();
        var housingResource = HousingResourceFromEntityAssembler.toResourceFromEntity(housingEntity);
        return ResponseEntity.ok(housingResource);
    }

    @GetMapping("/{realStateCompanyId}/housing")
    @Operation(summary = "Get all Housing", description = "Get all available Housing in the system by realStateCompanyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Housing retrieved successfully.")})
    public ResponseEntity<List<HousingResource>> getAllCurrency(@PathVariable Long realStateCompanyId) {
        var getAllHousingQuery = new GetAllHousingQuery(realStateCompanyId);
        var housing = housingQueryService.handle(getAllHousingQuery);
        var housingResources = housing.stream()
                .map(HousingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(housingResources);
    }
}
