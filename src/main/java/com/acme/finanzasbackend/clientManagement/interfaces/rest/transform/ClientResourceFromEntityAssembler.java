package com.acme.finanzasbackend.clientManagement.interfaces.rest.transform;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.clientManagement.interfaces.rest.resources.ClientResource;

public class ClientResourceFromEntityAssembler {
    public static ClientResource toResourceFromEntity(Client entity) {
        return new ClientResource(
                entity.getId(),
                entity.getRealStateCompanyId().realStateCompanyId(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getDni(),
                entity.getAge(),
                entity.getEmail(),
                entity.getIsWorking(),
                entity.getDependentsNumber(),
                entity.getMonthlyIncome(),
                entity.getIsDependent(),
                entity.getWorkingYears(),
                entity.getCurrency().getName()
        );
    }
}
