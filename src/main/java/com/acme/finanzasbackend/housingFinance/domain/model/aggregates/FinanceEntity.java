package com.acme.finanzasbackend.housingFinance.domain.model.aggregates;

import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityType;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
    private Double minYearsIndependentEmploymentTenure; // minimum of years working as independent
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
                         Double minYearsDependentEmploymentTenure, Double minYearsIndependentEmploymentTenure,
                         Boolean requiresCreditHistory) {
        this.name = name;
        this.type = type;
        this.minimumHousingPrice = minimumHousingPrice;
        this.maximumHousingPrice = maximumHousingPrice;
        this.minimumFinance = minimumFinance; // < 1 is percentage
        this.maximumFinance = maximumFinance; // < 1 is percentage
        this.minimumSalary = minimumSalary;
        this.minimumDownPaymentPercentage = minimumDownPaymentPercentage;
        this.maximumDownPaymentPercentage = maximumDownPaymentPercentage;
        this.allowsUsedHousing = allowsUsedHousing; // ---> from ServiceImpl
        this.inProjectMaxGracePeriodMonths = inProjectMaxGracePeriodMonths;
        this.generalMaxGracePeriodMonths = generalMaxGracePeriodMonths;
        this.minimumMonthPaymentTerm = minimumMonthPaymentTerm;
        this.maximumMonthPaymentTerm = maximumMonthPaymentTerm;
        this.allowsAnotherHousingFinancing = allowsAnotherHousingFinancing; // ---> from ServiceImpl
        this.minYearsDependentEmploymentTenure = minYearsDependentEmploymentTenure;
        this.minYearsIndependentEmploymentTenure = minYearsIndependentEmploymentTenure;
        this.requiresCreditHistory = requiresCreditHistory;
    }

    public Double getExactFinanceAmount(Double housingPrice) {
        if (Objects.equals(this.minimumFinance, this.maximumFinance)) {
            return housingPrice * this.maximumFinance;
        }
        return null;
    }

    public boolean isFinanceEntityAccepted(Double housingPrice, Double financeAmount,
                                           Double salary, Double downPaymentPercentage,
                                           HousingState housingState, Integer gracePeriodMonths,
                                           Boolean isDependent, Double workingYears,
                                           Boolean hasCreditHistory) {
        if (this.minimumHousingPrice != null && housingPrice < this.minimumHousingPrice) return false;
        if (this.maximumHousingPrice != null && housingPrice > this.maximumHousingPrice) return false;
        if (this.minimumFinance != null && this.minimumFinance > 1 && financeAmount < this.minimumFinance) return false;
        if (this.maximumFinance != null) {
            if (this.maximumFinance > 1 && financeAmount > this.maximumFinance) return false;
            else if (financeAmount > this.maximumFinance * housingPrice) return false;
        }
        if (this.minimumSalary != null && salary < this.minimumSalary) return false;
        if (this.minimumDownPaymentPercentage != null && downPaymentPercentage < this.minimumDownPaymentPercentage) return false;
        if (this.maximumDownPaymentPercentage != null && downPaymentPercentage > this.maximumDownPaymentPercentage) return false;
        if (housingState == HousingState.EN_PROYECTO && this.inProjectMaxGracePeriodMonths != null && gracePeriodMonths > this.inProjectMaxGracePeriodMonths) return false;
        if (housingState != HousingState.EN_PROYECTO && this.generalMaxGracePeriodMonths != null && gracePeriodMonths > this.generalMaxGracePeriodMonths) return false;
        if (isDependent) {
            if (this.minYearsDependentEmploymentTenure != null && workingYears < this.minYearsDependentEmploymentTenure) return false;
        } else if (this.minYearsIndependentEmploymentTenure != null && workingYears < this.minYearsIndependentEmploymentTenure) return false;
        if (this.requiresCreditHistory && !hasCreditHistory) return false;
        return true;
    }
}
