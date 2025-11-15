package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

public record CreateInterestRateCommand(
        String type,
        String period,
        Double percentage,
        String nominalCapitalization
) {
    public CreateInterestRateCommand {
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
