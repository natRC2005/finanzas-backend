package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record GracePeriodResource(
        Long id,
        String type,
        Integer months
) {
}
