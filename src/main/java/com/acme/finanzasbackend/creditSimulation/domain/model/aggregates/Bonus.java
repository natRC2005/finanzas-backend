package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
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
    private Double givenAmount;

    public Bonus() {}

    public Bonus(Boolean isRequired, HousingState housingState, Double housingSalePrice) {
        if (isRequired) {
            if (housingSalePrice > 68800.00 && housingSalePrice <= 98100.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 33700.00;
                else this.givenAmount = 27400.00;
            }
            else if (housingSalePrice > 98100.00 && housingSalePrice <= 146900.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 29100.00;
                else this.givenAmount = 22800.00;
            }
            else if (housingSalePrice > 146900.00 && housingSalePrice <= 244600.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 27200.00;
                else this.givenAmount = 20900.00;
            }
            else if (housingSalePrice > 244600.00 && housingSalePrice <= 362100.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 14100.00;
                else this.givenAmount = 7800.00;
            }
            else {
                this.isApplied = false;
                this.givenAmount = 0.00;
            }
        }
        else {
            this.isApplied = false;
            this.givenAmount = 0.00;
        }
    }
}
