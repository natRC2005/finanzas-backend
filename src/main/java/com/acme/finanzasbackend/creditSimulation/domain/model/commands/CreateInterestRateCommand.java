package com.acme.finanzasbackend.creditSimulation.domain.model.commands;

public record CreateInterestRateCommand(
        String type,
        String capitalization,
        Double percentage
) {
    public CreateInterestRateCommand {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (type.equals("NOMINAL") && capitalization == null) {
            throw new IllegalArgumentException("Capitalization cannot be null");
        }
        if (percentage == null || percentage < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative");
        }
    }
}
