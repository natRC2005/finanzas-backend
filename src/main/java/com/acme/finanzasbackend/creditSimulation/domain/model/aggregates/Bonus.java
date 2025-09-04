package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Bfh;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bonus extends AuditableAbstractAggregateRoot<Bonus> {
    private Boolean isApplied;
    private Double visMaxValue;

    @Embedded
    private Bfh bfh;
}
