package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Totals;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.TotalsResource;

public class TotalsResourceFromEntityAssembler {
    public static TotalsResource toResourceFromEntity(Totals entity) {
        return new TotalsResource(
                entity.totalInterest() != null ? entity.totalInterest() : null,
                entity.totalCapitalAmortization() != null ? entity.totalCapitalAmortization() : null,
                entity.totaLifeInsurance() != null ? entity.totaLifeInsurance() : null,
                entity.totalRiskInsurance() != null ? entity.totalRiskInsurance() : null,
                entity.totalPeriodicCommission() != null ? entity.totalPeriodicCommission() : null,
                entity.totalAdministrationFees() != null ? entity.totalAdministrationFees() : null
        );
    }
}
