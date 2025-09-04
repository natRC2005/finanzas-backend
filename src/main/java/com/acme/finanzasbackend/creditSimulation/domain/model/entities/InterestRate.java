package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Capitalization;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.InterestRateType;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class InterestRate extends AuditableModel {
    private InterestRateType type;
    private Capitalization capitalization;
    private Double percentage;
}
