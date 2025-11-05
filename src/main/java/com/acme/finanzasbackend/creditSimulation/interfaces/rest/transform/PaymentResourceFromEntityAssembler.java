package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.Payment;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getFee(),
                entity.getInterest(),
                entity.getAmortization(),
                entity.getLifeInsuranceFee(),
                entity.getHousingInsuranceFee(),
                entity.getBalance()
        );
    }
}
