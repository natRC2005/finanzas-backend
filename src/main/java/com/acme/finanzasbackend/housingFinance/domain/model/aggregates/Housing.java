package com.acme.finanzasbackend.housingFinance.domain.model.aggregates;

import com.acme.finanzasbackend.clientManagement.domain.model.commands.CreateClientCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.CreateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.UpdateHousingCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingCategory;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.Province;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Housing extends AuditableAbstractAggregateRoot<Housing> {
    @NotNull
    @Embedded
    private RealStateCompanyId realStateCompanyId;

    private String title;
    private String description;
    private Province province;
    private String district;
    private String address;

    private String department; // ej: 304, 102A

    private Double area;
    private Integer roomQuantity;
    private Double salePrice;
    private HousingState housingState;
    private HousingCategory housingCategory;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public Housing() {}

    public Housing(CreateHousingCommand command, Currency currency) {
        this.realStateCompanyId = new RealStateCompanyId(command.realStateCompanyId());
        this.title = command.title();
        this.description = command.description();
        this.province = Province.valueOf(command.province());
        this.district = command.district();
        this.address = command.address();
        this.department = command.department();
        this.area = command.area();
        this.roomQuantity = command.roomQuantity();
        this.salePrice = command.salePrice();
        this.housingState = HousingState.valueOf(command.housingState());
        this.housingCategory = HousingCategory.valueOf(command.housingCategory());
        this.currency = currency;
    }

    public void modifyHousing(UpdateHousingCommand command, Currency currency) {
        this.title = command.title();
        this.description = command.description();
        this.province = Province.valueOf(command.province());
        this.district = command.district();
        this.address = command.address();
        this.department = command.department();
        this.area = command.area();
        this.roomQuantity = command.roomQuantity();
        this.salePrice = command.salePrice();
        this.housingState = HousingState.valueOf(command.housingState());
        this.currency = currency;
    }

    public void exchangeSalePriceCurrency(Currency currency) {
        if (this.currency != currency) {
            this.salePrice = this.currency.exchangeCurrency(this.salePrice);
            this.currency = currency;
        }
    }
}
