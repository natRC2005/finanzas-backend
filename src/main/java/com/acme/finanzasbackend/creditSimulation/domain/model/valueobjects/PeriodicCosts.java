package com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PeriodicCosts(
        Double periodicCommission,
        Double shippingCosts, // portes
        Double administrationExpenses,
        Double lifeInsurance, // desgravamen
        Double riskInsurance,
        Double monthlyStatementDelivery // envio de estado de cuenta
) {
    public PeriodicCosts {}

    public Double getTotalPeriodicCosts() {
        return periodicCommission + shippingCosts + administrationExpenses +
                lifeInsurance + riskInsurance + monthlyStatementDelivery;
    }
}
