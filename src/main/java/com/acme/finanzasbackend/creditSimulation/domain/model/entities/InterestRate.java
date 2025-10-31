package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateInterestRateCommand;
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

    public InterestRate() {}

    public InterestRate(CreateInterestRateCommand command) {
        this.type = InterestRateType.valueOf(command.type());
        if (this.type == InterestRateType.EFECTIVA) this.capitalization = null;
        else this.capitalization = Capitalization.valueOf(command.capitalization());
        this.percentage = command.percentage();
    }
}
