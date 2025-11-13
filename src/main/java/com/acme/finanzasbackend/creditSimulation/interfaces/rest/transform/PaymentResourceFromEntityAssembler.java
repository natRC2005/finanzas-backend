package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getTem(),
                entity.getGracePeriodType(),
                entity.getInitialBalance(),
                entity.getInterest(),
                entity.getFee(),
                entity.getAmortization(),
                entity.getPeriodicCosts(),
                entity.getFinalBalance(),
                entity.getCashFlow()
        );
    }
}
