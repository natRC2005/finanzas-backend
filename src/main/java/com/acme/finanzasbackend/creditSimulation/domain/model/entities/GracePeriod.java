package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateGracePeriodCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.GracePeriodType;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class GracePeriod extends AuditableModel {
    private GracePeriodType type;
    private Integer months;

    public GracePeriod() {}

    public GracePeriod(CreateGracePeriodCommand command) {
        this.type = GracePeriodType.valueOf(command.type());
        this.months = command.months();
        if (this.type == GracePeriodType.NULL) this.months = 0;
    }

    public GracePeriod(String type,
                       Integer months) {
        this.type = GracePeriodType.valueOf(type);
        this.months = months;
        if (this.type == GracePeriodType.NULL) this.months = 0;
    }
}
