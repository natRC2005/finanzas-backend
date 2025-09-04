package com.acme.finanzasbackend.shared.domain.model.valueobjects;

import com.acme.finanzasbackend.shared.domain.exceptions.RealStateCompanyIdNotValidException;
import jakarta.persistence.Embeddable;

@Embeddable
public record RealStateCompanyId(Long realStateCompanyId) {
    public RealStateCompanyId {
        if (realStateCompanyId == null || realStateCompanyId <= 0) {
            throw new RealStateCompanyIdNotValidException();
        }
    }
}
