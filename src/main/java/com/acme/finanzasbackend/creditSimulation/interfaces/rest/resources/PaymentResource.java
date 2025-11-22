package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.GracePeriodType;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.PeriodicCosts;

import java.time.LocalDate;

public record PaymentResource(
        Long id,
        LocalDate paymentDate,
        Integer orderNumber,
        Double tem, // Tasa Efectiva Mensual
        GracePeriodType gracePeriodType,
        Double initialBalance, // Saldo Inicial
        Double interest,
        Double fee, // Cuota
        Double amortization,
        PeriodicCosts periodicCosts,
        Double finalBalance, // Saldo Final
        Double cashFlow // Flujo
) {
}
