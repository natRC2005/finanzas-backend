package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.GracePeriodType;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class GracePeriod extends AuditableModel {
    private GracePeriodType type;
    private Integer months;
}
