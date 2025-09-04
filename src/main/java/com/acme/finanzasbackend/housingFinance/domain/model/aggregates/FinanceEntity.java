package com.acme.finanzasbackend.housingFinance.domain.model.aggregates;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityType;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FinanceEntity extends AuditableAbstractAggregateRoot<FinanceEntity> {
    private String name;
    private FinanceEntityType type;
}
