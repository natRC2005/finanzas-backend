package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateBonusCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.BonusType;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
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
    private BonusType bonusType;

    // Convert to soles everytime -> compare currency

    public Bonus() {}

    // Ask for Housing Category

    public Bonus(Boolean isRequired, HousingState housingState, Double housingSalePrice, Currency currency) {
        if (isRequired) {
            Double salePrice = getHousingSalePriceInSoles(currency, housingSalePrice);
            if (salePrice > 68800.00 && salePrice <= 98100.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO || housingState == HousingState.SEGUNDA) {
                    // this.bonusType = Bo
                    this.givenAmount = 33700.00;
                }
                else this.givenAmount = 27400.00;
            }
            else if (salePrice > 98100.00 && salePrice <= 146900.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 29100.00;
                else this.givenAmount = 22800.00;
            }
            else if (salePrice > 146900.00 && salePrice <= 244600.00) {
                this.isApplied = true;
                if (housingState == HousingState.NUEVO) this.givenAmount = 27200.00;
                else this.givenAmount = 20900.00;
            }
            else if (salePrice > 244600.00 && salePrice <= 362100.00) {
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

    public Bonus(CreateBonusCommand command) {
        if (command.isRequired()) {
            if (command.housingSalePrice() > 68800.00 && command.housingSalePrice() <= 98100.00) {
                this.isApplied = true;
                if (command.housingState() == HousingState.NUEVO) this.givenAmount = 33700.00;
                else this.givenAmount = 27400.00;
            }
            else if (command.housingSalePrice() > 98100.00 && command.housingSalePrice() <= 146900.00) {
                this.isApplied = true;
                if (command.housingState() == HousingState.NUEVO) this.givenAmount = 29100.00;
                else this.givenAmount = 22800.00;
            }
            else if (command.housingSalePrice() > 146900.00 && command.housingSalePrice() <= 244600.00) {
                this.isApplied = true;
                if (command.housingState() == HousingState.NUEVO) this.givenAmount = 27200.00;
                else this.givenAmount = 20900.00;
            }
            else if (command.housingSalePrice() > 244600.00 && command.housingSalePrice() <= 362100.00) {
                this.isApplied = true;
                if (command.housingState() == HousingState.NUEVO) this.givenAmount = 14100.00;
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

    private Double getHousingSalePriceInSoles(Currency currency, Double housingSalePrice) {
        if (currency.getId() == 1) return housingSalePrice;
        return currency.exchangeCurrency(housingSalePrice);
    }
}

// UPDATE -> Use for 2 kinds of bonuses, so that the user can choose to take
// a bonus and the ideal one is applies (compare quantities)