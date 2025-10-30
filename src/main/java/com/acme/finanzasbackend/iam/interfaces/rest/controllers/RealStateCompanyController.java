package com.acme.finanzasbackend.iam.interfaces.rest.controllers;

import com.acme.finanzasbackend.iam.domain.model.queries.GetRealStateCompanyByIdQuery;
import com.acme.finanzasbackend.iam.domain.services.RealStateCompanyCommandService;
import com.acme.finanzasbackend.iam.domain.services.RealStateCompanyQueryService;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.AuthenticatedResource;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.RealStateCompanyResource;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.SignInResource;
import com.acme.finanzasbackend.iam.interfaces.rest.resources.SignUpResource;
import com.acme.finanzasbackend.iam.interfaces.rest.transform.AuthenticatedResourceFromEntityAssembler;
import com.acme.finanzasbackend.iam.interfaces.rest.transform.RealStateCompanyResourceFromEntityAssembler;
import com.acme.finanzasbackend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.acme.finanzasbackend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/real-state-company", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Real State Company", description = "Available Real State Company Endpoints")
public class RealStateCompanyController {
    private final RealStateCompanyCommandService realStateCompanyCommandService;
    private final RealStateCompanyQueryService realStateCompanyQueryService;

    public RealStateCompanyController(RealStateCompanyCommandService realStateCompanyCommandService, RealStateCompanyQueryService realStateCompanyQueryService) {
        this.realStateCompanyCommandService = realStateCompanyCommandService;
        this.realStateCompanyQueryService = realStateCompanyQueryService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Create a new Real State Company", description = "Create a new Real State Company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Real State Company created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Real State Company not found")})
    public ResponseEntity<RealStateCompanyResource> signUp(@RequestBody SignUpResource resource) {
        var createCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var userId = realStateCompanyCommandService.handle(createCommand);
        if (userId == null) return ResponseEntity.badRequest().build();
        var getUserByIdQuery = new GetRealStateCompanyByIdQuery(userId);
        var user = realStateCompanyQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userEntity = user.get();
        var userResource = RealStateCompanyResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in as a Real State Company", description = "Sign in as a Real State Company representative")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Real State Company signed in"),
            @ApiResponse(responseCode = "404", description = "Real State Company not found")})
    public ResponseEntity<AuthenticatedResource> signIn(@RequestBody SignInResource resource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = realStateCompanyCommandService.handle(signInCommand);
        if (user.isEmpty()) return ResponseEntity.badRequest().build();
        var userEntity = user.get();
        var authenticatedResource = AuthenticatedResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return ResponseEntity.ok(authenticatedResource);
    }

    @GetMapping("/{realStateCompanyId}")
    @Operation(summary = "Get Real State Company by id", description = "Get Real State Company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Real State Company found"),
            @ApiResponse(responseCode = "404", description = "Real State Company not found")
    })
    public ResponseEntity<RealStateCompanyResource> getRealStateCompanyById(@PathVariable Long realStateCompanyId) {
        var getRealStateCompanyByIdQuery = new GetRealStateCompanyByIdQuery(realStateCompanyId);
        var realStateCompany = realStateCompanyQueryService.handle(getRealStateCompanyByIdQuery);
        if (realStateCompany.isEmpty()) return ResponseEntity.badRequest().build();
        var realStateCompanyEntity = realStateCompany.get();
        var realStateCompanyResource = RealStateCompanyResourceFromEntityAssembler.toResourceFromEntity(realStateCompanyEntity);
        return ResponseEntity.ok(realStateCompanyResource);
    }


}
