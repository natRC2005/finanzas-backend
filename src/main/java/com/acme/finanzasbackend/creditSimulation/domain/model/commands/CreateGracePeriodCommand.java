package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

public record CreateGracePeriodCommand(
        String type,
        Integer months
) {
    public CreateGracePeriodCommand {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (months == null || months < 1) {
            throw new IllegalArgumentException("months must not be less than 1");
        }
    }
}
