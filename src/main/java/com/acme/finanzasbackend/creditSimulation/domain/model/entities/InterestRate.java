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
    private Period nominalCapitalization;
    private Double tem;

    public InterestRate() {}

    public InterestRate(CreateInterestRateCommand command) {
        this.type = InterestRateType.valueOf(command.type());
        this.period = Period.valueOf(command.period());
        this.percentage = command.percentage() / 100.0;
        this.nominalCapitalization = Period.valueOf(command.nominalCapitalization());
        this.tem = getEffectiveMonthlyRate();
    }

    public InterestRate(String type,
                        String period,
                        Double percentage,
                        String nominalCapitalization) {
        this.type = InterestRateType.valueOf(type);
        this.period = Period.valueOf(period);
        this.percentage = percentage / 100.0;
        this.nominalCapitalization = Period.valueOf(nominalCapitalization);
        this.tem = getEffectiveMonthlyRate();
    }

    public double getEffectiveMonthlyRate() {
        double m = switch (this.period) {
            case DIARIA -> 1.0;
            case QUINCENAL -> 15.0;
            case MENSUAL -> 30.0;
            case BIMESTRAL -> 60.0;
            case TRIMESTRAL -> 90.0;
            case CUATRIMESTRAL -> 120.0;
            case SEMESTRAL -> 180.0;
            case ANUAL -> 360.0;
            case NULL -> 0.0;
        };

        if (type == InterestRateType.NOMINAL) {
            double cap = switch (this.nominalCapitalization) {
                case DIARIA -> 1.0;
                case QUINCENAL -> 15.0;
                case MENSUAL -> 30.0;
                case BIMESTRAL -> 60.0;
                case TRIMESTRAL -> 90.0;
                case CUATRIMESTRAL -> 120.0;
                case SEMESTRAL -> 180.0;
                case ANUAL -> 360.0;
                case NULL -> 0.0;
            };

            return Math.pow(1 + (this.percentage / (m / cap)), (30.0 / cap)) - 1;
        }

        return Math.pow(1 + this.percentage, 30.0 / m) - 1;
    }
}
