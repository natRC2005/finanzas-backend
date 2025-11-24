package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record CreateGracePeriodResource(
        String type,
        Integer months
) {
    public CreateGracePeriodResource {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (months == null || months < 0 || months > 6) {
            throw new IllegalArgumentException("months must not be less than 0 or more than 6");
        }
    }
}
