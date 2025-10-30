package com.acme.finanzasbackend.iam.domain.model.commands;

public record UpdateRealStateCompanyCommand(
        Long id,
        String companyName,
        String username,
        String ruc,
        String companyEmail,
        String password
) {
    public UpdateRealStateCompanyCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (ruc == null || ruc.length() != 11) {
            throw new IllegalArgumentException("RUC should be 11 digits long");
        }
        if (companyEmail == null || companyEmail.isEmpty()) {
            throw new IllegalArgumentException("Company email cannot be null or empty");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password should be at least 8 characters");
        }
    }
}
