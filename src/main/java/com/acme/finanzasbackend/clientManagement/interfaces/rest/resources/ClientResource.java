package com.acme.finanzasbackend.clientManagement.interfaces.rest.resources;

public record ClientResource(
        Long id,
        Long realStateCompanyId,
        String firstname,
        String lastname,
        String dni,
        Integer age,
        String email,
        Boolean isWorking,
        Integer dependentsNumber,
        Double monthlyIncome,
        Boolean isDependent,
        Double workingYears,
        Boolean isIntegrator,
        String currencySymbol
) {
}
