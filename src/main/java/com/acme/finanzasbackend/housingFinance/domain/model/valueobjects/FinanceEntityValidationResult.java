package com.acme.finanzasbackend.housingFinance.domain.model.valueobjects;

public record FinanceEntityValidationResult(Boolean accepted, String reason) {
    public FinanceEntityValidationResult {
        if (accepted == null) {
            throw new IllegalArgumentException("accepted cannot be null");
        }
        if (reason == null) {
            throw new IllegalArgumentException("Reason cannot be null or empty");
        }
    }
}

