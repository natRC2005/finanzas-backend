package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateInterestRateCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.Period;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.InterestRateType;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class InterestRate extends AuditableModel {
    private InterestRateType type;
    private Period period;
    private Double percentage;

    public InterestRate() {}

    public InterestRate(CreateInterestRateCommand command) {
        this.type = InterestRateType.valueOf(command.type());
        if (this.type == InterestRateType.EFECTIVA) this.period = null;
        else this.period = Period.valueOf(command.period());
        this.percentage = command.percentage();
    }

    public InterestRate(String type,
                        String period,
                        Double percentage) {
        this.type = InterestRateType.valueOf(type);
        if (this.type == InterestRateType.EFECTIVA) this.period = null;
        else this.period = Period.valueOf(period);
        this.percentage = percentage;
    }

    public double getEffectiveMonthlyRate() {
        if (type == InterestRateType.EFECTIVA) {
            // Asumimos que la tasa es efectiva anual
            return Math.pow(1 + this.percentage, 1.0 / 12.0) - 1;
        }

        int m = switch (this.period) {
            case DIARIA -> 360;
            case QUINCENAL -> 24;
            case MENSUAL -> 12;
            case BIMESTRAL -> 6;
            case TRIMESTRAL -> 4;
            case CUATRIMESTRAL -> 3;
            case SEMESTRAL -> 2;
            case ANUAL -> 1;
        };

        double effectiveRate = Math.pow(1 + (this.percentage / m), m) - 1;
        return Math.pow(1 + effectiveRate, 1.0 / 12.0) - 1;
    }
}
