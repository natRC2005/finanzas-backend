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

    private Double minimumHousingPrice;
    private Double maximumHousingPrice;
    private Double minimumFinance;
    private Double maximumFinance;
    private Double minimumSalary;
    private Double minimumDownPaymentPercentage;
    private Double maximumDownPaymentPercentage;

    private Boolean allowsUsedHousing;
    private Integer inProjectMaxGracePeriodMonths;
    private Integer generalMaxGracePeriodMonths;

    private Integer minimumMonthPaymentTerm;
    private Integer maximumMonthPaymentTerm;

    private Boolean allowsAnotherHousingFinancing; // exists function
    private Double minYearsDependentEmploymentTenure; // minimum of years working as dependent
    private Double maxYearsIndependentEmploymentTenure; // minimum of years working as independent
    private Boolean requiresCreditHistory;

    public FinanceEntity() {}

    public FinanceEntity(String name, FinanceEntityType type,
                         Double minimumHousingPrice, Double maximumHousingPrice,
                         Double minimumFinance, Double maximumFinance, Double minimumSalary,
                         Double minimumDownPaymentPercentage, Double maximumDownPaymentPercentage,
                         Boolean allowsUsedHousing, Integer inProjectMaxGracePeriodMonths,
                         Integer generalMaxGracePeriodMonths,
                         Integer minimumMonthPaymentTerm, Integer maximumMonthPaymentTerm,
                         Boolean allowsAnotherHousingFinancing,
                         Double minYearsDependentEmploymentTenure, Double maxYearsIndependentEmploymentTenure,
                         Boolean requiresCreditHistory) {
        this.name = name;
        this.type = type;
        this.minimumHousingPrice = minimumHousingPrice;
        this.maximumHousingPrice = maximumHousingPrice;
        this.minimumFinance = minimumFinance;
        this.maximumFinance = maximumFinance;
        this.minimumSalary = minimumSalary;
        this.minimumDownPaymentPercentage = minimumDownPaymentPercentage;
        this.maximumDownPaymentPercentage = maximumDownPaymentPercentage;
        this.allowsUsedHousing = allowsUsedHousing;
        this.inProjectMaxGracePeriodMonths = inProjectMaxGracePeriodMonths;
        this.generalMaxGracePeriodMonths = generalMaxGracePeriodMonths;
        this.minimumMonthPaymentTerm = minimumMonthPaymentTerm;
        this.maximumMonthPaymentTerm = maximumMonthPaymentTerm;
        this.allowsAnotherHousingFinancing = allowsAnotherHousingFinancing;
        this.minYearsDependentEmploymentTenure = minYearsDependentEmploymentTenure;
        this.maxYearsIndependentEmploymentTenure = maxYearsIndependentEmploymentTenure;
        this.requiresCreditHistory = requiresCreditHistory;
    }
}
