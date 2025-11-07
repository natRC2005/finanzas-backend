package com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources;

public record CreateInterestRateResource(
        String type,
        String period,
        Double percentage
) {
    public CreateInterestRateResource {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (period == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }
        if (percentage == null || percentage < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative");
        }
    }
}
