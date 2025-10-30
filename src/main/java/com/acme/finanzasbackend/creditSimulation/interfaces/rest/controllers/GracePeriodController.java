package com.acme.finanzasbackend.creditSimulation.interfaces.rest.controllers;

import com.acme.finanzasbackend.creditSimulation.domain.model.queries.GetGracePeriodByIdQuery;
import com.acme.finanzasbackend.creditSimulation.domain.services.GracePeriodCommandService;
import com.acme.finanzasbackend.creditSimulation.domain.services.GracePeriodQueryService;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreateGracePeriodResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.GracePeriodResource;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.CreateGracePeriodCommandFromResourceAssembler;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform.GracePeriodResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/grace-periods", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Grace Periods", description = "Available Grace Periods Endpoints")
public class GracePeriodController {
    private final GracePeriodCommandService gracePeriodCommandService;
    private final GracePeriodQueryService gracePeriodQueryService;

    public GracePeriodController(GracePeriodCommandService gracePeriodCommandService, GracePeriodQueryService gracePeriodQueryService) {
        this.gracePeriodCommandService = gracePeriodCommandService;
        this.gracePeriodQueryService = gracePeriodQueryService;
    }

    @PostMapping("/grace-periods")
    @Operation(summary = "Create a new Grace Period", description = "Create a new Grace Period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grace Period created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Grace Period not found")})
    public ResponseEntity<GracePeriodResource> createGracePeriod(@RequestBody CreateGracePeriodResource resource) {
        var createCommand = CreateGracePeriodCommandFromResourceAssembler.toCommandFromResource(resource);
        var gracePeriodId = gracePeriodCommandService.handle(createCommand);
        if (gracePeriodId == null || gracePeriodId == 0L) return ResponseEntity.badRequest().build();
        var getGracePeriodByIdQuery = new GetGracePeriodByIdQuery(gracePeriodId);
        var gracePeriodItem = gracePeriodQueryService.handle(getGracePeriodByIdQuery);
        if (gracePeriodItem.isEmpty()) return ResponseEntity.notFound().build();
        var gracePeriodEntity = gracePeriodItem.get();
        var gracePeriodResource = GracePeriodResourceFromEntityAssembler.toResourceFromEntity(gracePeriodEntity);
        return new ResponseEntity<>(gracePeriodResource, HttpStatus.CREATED);
    }

    @GetMapping("/{gracePeriodId}")
    @Operation(summary = "Get Grace Period by id", description = "Get Grace Period by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grace Period found"),
            @ApiResponse(responseCode = "404", description = "Grace Period not found")})
    public ResponseEntity<GracePeriodResource> getGracePeriodById(@PathVariable Long gracePeriodId) {
        var getGracePeriodByIdQuery = new GetGracePeriodByIdQuery(gracePeriodId);
        var gracePeriod = gracePeriodQueryService.handle(getGracePeriodByIdQuery);
        if (gracePeriod.isEmpty()) return ResponseEntity.notFound().build();
        var gracePeriodEntity = gracePeriod.get();
        var gracePeriodResource = GracePeriodResourceFromEntityAssembler.toResourceFromEntity(gracePeriodEntity);
        return ResponseEntity.ok(gracePeriodResource);
    }
}
