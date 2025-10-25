package com.acme.finanzasbackend.clientManagement.domain.model.commands;

import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;

public record CreateClientCommand(
        Long id,
        RealStateCompanyId realStateCompanyId,
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
        Long currencyId
) {
}
