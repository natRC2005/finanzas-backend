package com.acme.finanzasbackend.shared.domain.exceptions;

public class RealStateCompanyIdNotValidException extends RuntimeException {
    public RealStateCompanyIdNotValidException() {
        super("This real state company id is not valid");
    }
}
