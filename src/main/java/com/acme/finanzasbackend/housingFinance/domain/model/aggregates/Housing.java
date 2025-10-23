package com.acme.finanzasbackend.housingFinance.domain.model.aggregates;

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

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

}
