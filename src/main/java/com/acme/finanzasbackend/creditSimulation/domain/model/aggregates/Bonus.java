package com.acme.finanzasbackend.creditSimulation.domain.model.aggregates;

import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateBonusCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.BonusType;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingCategory;
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

    public Bonus() {}

    // Ask for Housing Category

    public Bonus(Boolean isRequired, HousingCategory housingCategory,
                 Double housingSalePrice, Currency currency, Boolean isIntegrator) {
        if (isRequired) {
            Double salePrice = getHousingSalePriceInSoles(currency, housingSalePrice);
            if (salePrice > 68800.00 && salePrice <= 98100.00) {
                this.isApplied = true;
                if (housingCategory == HousingCategory.TRADICIONAL) {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 31000.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 27400.00;
                    }
                } else {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 37300.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 33700.00;
                    }
                }
            }
            else if (salePrice > 98100.00 && salePrice <= 146900.00) {
                this.isApplied = true;
                if (housingCategory == HousingCategory.TRADICIONAL) {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 26400.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 22800.00;
                    }
                } else {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 32700.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 29100.00;
                    }
                }
            }
            else if (salePrice > 146900.00 && salePrice <= 244600.00) {
                this.isApplied = true;
                if (housingCategory == HousingCategory.TRADICIONAL) {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 24500.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 20900.00;
                    }
                } else {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 30800.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 27200.00;
                    }
                }
            }
            else if (salePrice > 244600.00 && salePrice <= 362100.00) {
                this.isApplied = true;
                if (housingCategory == HousingCategory.TRADICIONAL) {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 11400.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 7800.00;
                    }
                } else {
                    if (isIntegrator) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 17700.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 14100.00;
                    }
                }
            }
            else {
                this.bonusType = BonusType.NULL;
                this.isApplied = false;
                this.givenAmount = 0.00;
            }
        }
        else {
            this.bonusType = BonusType.NULL;
            this.isApplied = false;
            this.givenAmount = 0.00;
        }
    }

    public Bonus(CreateBonusCommand command) {
        if (command.isRequired()) {
            Double salePrice = getHousingSalePriceInSoles(command.currency(), command.housingSalePrice());
            if (salePrice > 68800.00 && salePrice <= 98100.00) {
                this.isApplied = true;
                if (command.housingCategory() == HousingCategory.TRADICIONAL) {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 31000.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 27400.00;
                    }
                } else {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 37300.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 33700.00;
                    }
                }
            }
            else if (salePrice > 98100.00 && salePrice <= 146900.00) {
                this.isApplied = true;
                if (command.housingCategory() == HousingCategory.TRADICIONAL) {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 26400.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 22800.00;
                    }
                } else {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 32700.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 29100.00;
                    }
                }
            }
            else if (salePrice > 146900.00 && salePrice <= 244600.00) {
                this.isApplied = true;
                if (command.housingCategory() == HousingCategory.TRADICIONAL) {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 24500.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 20900.00;
                    }
                } else {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 30800.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 27200.00;
                    }
                }
            }
            else if (salePrice > 244600.00 && salePrice <= 362100.00) {
                this.isApplied = true;
                if (command.housingCategory() == HousingCategory.TRADICIONAL) {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_TRADICIONAL;
                        this.givenAmount = 11400.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_TRADICIONAL;
                        this.givenAmount = 7800.00;
                    }
                } else {
                    if (command.isIntegrator()) {
                        this.bonusType = BonusType.INTEGRADOR_SOSTENIBLE;
                        this.givenAmount = 17700.00;
                    } else {
                        this.bonusType = BonusType.VIVIENDA_SOSTENIBLE;
                        this.givenAmount = 14100.00;
                    }
                }
            }
            else {
                this.bonusType = BonusType.NULL;
                this.isApplied = false;
                this.givenAmount = 0.00;
            }
        }
        else {
            this.bonusType = BonusType.NULL;
            this.isApplied = false;
            this.givenAmount = 0.00;
        }
    }

    private Double getHousingSalePriceInSoles(Currency currency, Double housingSalePrice) {
        if (currency.getId() == 1) return housingSalePrice;
        return currency.exchangeCurrency(housingSalePrice);
    }
}
