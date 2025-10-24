package com.acme.finanzasbackend.iam.interfaces.rest.resources;

public record SignInResource(String username, String password) {
    public SignInResource {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }
    }
}
