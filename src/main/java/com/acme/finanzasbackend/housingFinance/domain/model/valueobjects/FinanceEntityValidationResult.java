package com.acme.finanzasbackend.housingFinance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
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

