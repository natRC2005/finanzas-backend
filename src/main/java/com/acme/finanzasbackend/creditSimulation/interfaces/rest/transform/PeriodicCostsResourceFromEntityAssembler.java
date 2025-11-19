package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.PeriodicCosts;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.PeriodicCostsResource;

public class PeriodicCostsResourceFromEntityAssembler {
    public static PeriodicCostsResource toResourceFromEntity(PeriodicCosts entity) {
        return new PeriodicCostsResource(
                entity.periodicCommission() != null ? entity.periodicCommission() : null,
                entity.shippingCosts() != null ? entity.shippingCosts() : null,
                entity.administrationExpenses() != null ? entity.administrationExpenses() : null,
                entity.lifeInsurance() != null ? entity.lifeInsurance() : null,
                entity.riskInsurance() != null ? entity.riskInsurance() : null,
                entity.monthlyStatementDelivery() != null ? entity.monthlyStatementDelivery() : null
        );
    }
}
