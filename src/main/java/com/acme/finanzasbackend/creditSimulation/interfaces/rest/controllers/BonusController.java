package com.acme.finanzasbackend.creditSimulation.interfaces.rest.controllers;

import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetBonusByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.BonusCommandService;
import com.acme.finanzasbackend.creditSimulation.domain.services.BonusQueryService;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateBonusResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.BonusResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.CreateBonusCommandFromResourceAssembler;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.BonusResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/bonuses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Bonuses", description = "Available Bonuses Endpoints")
public class BonusController {
    private final BonusCommandService bonusCommandService;
    private final BonusQueryService bonusQueryService;

    public BonusController(BonusCommandService bonusCommandService, BonusQueryService bonusQueryService) {
        this.bonusCommandService = bonusCommandService;
        this.bonusQueryService = bonusQueryService;
    }

    @PostMapping("/bonuses")
    @Operation(summary = "Create a new Bonus", description = "Create a new Bonus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bonus created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Bonus not found")})
    public ResponseEntity<BonusResource> createBonus(@RequestBody CreateBonusResource resource) {
        var createCommand = CreateBonusCommandFromResourceAssembler.toCommandFromResource(resource);
        var bonusId = bonusCommandService.handle(createCommand);
        if (bonusId == null || bonusId == 0L) return ResponseEntity.badRequest().build();
        var getBonusByIdQuery = new GetBonusByIdQuery(bonusId);
        var bonusItem = bonusQueryService.handle(getBonusByIdQuery);
        if (bonusItem.isEmpty()) return ResponseEntity.notFound().build();
        var bonusEntity = bonusItem.get();
        var bonusResource = BonusResourceFromEntityAssembler.toResourceFromEntity(bonusEntity);
        return new ResponseEntity<>(bonusResource, HttpStatus.CREATED);
    }

    @GetMapping("/{bonusId}")
    @Operation(summary = "Get Bonus by id", description = "Get Bonus by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bonus found"),
            @ApiResponse(responseCode = "404", description = "Bonus not found")})
    public ResponseEntity<BonusResource> getBonusById(@PathVariable Long bonusId) {
        var getBonusByIdQuery = new GetBonusByIdQuery(bonusId);
        var bonus = bonusQueryService.handle(getBonusByIdQuery);
        if (bonus.isEmpty()) return ResponseEntity.notFound().build();
        var bonusEntity = bonus.get();
        var bonusResource = BonusResourceFromEntityAssembler.toResourceFromEntity(bonusEntity);
        return ResponseEntity.ok(bonusResource);
    }
}
