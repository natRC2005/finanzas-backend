package com.acme.finanzasbackend.iam.domain.model.commands;

public record SignInCommand(String username, String password) {
    public SignInCommand {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }
    }
}
