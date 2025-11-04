package com.acme.finanzasbackend.housingFinance.domain.model.aggregates;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.EvaluateFinanceEntityCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityType;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityValidationResult;
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
        this.allowsUsedHousing = allowsUsedHousing; // ---> boolean
        this.inProjectMaxGracePeriodMonths = inProjectMaxGracePeriodMonths;
        this.generalMaxGracePeriodMonths = generalMaxGracePeriodMonths;
        this.minimumMonthPaymentTerm = minimumMonthPaymentTerm;
        this.maximumMonthPaymentTerm = maximumMonthPaymentTerm;
        this.allowsAnotherHousingFinancing = allowsAnotherHousingFinancing; // ---> boolean
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

    public FinanceEntityValidationResult isFinanceEntityAccepted(
            Double housingPrice, Double financeAmount,
            Double salary, Double downPaymentPercentage,
            HousingState housingState, Integer gracePeriodMonths,
            Boolean isDependent, Double workingYears,
            Boolean hasCreditHistory, Boolean isHousingUsed,
            Boolean hasAnotherHousingFinancing) {
        if (this.minimumHousingPrice != null && housingPrice < this.minimumHousingPrice)
            return new FinanceEntityValidationResult(false, "El precio de la vivienda es menor al mínimo permitido.");
        if (this.maximumHousingPrice != null && housingPrice > this.maximumHousingPrice)
            return new FinanceEntityValidationResult(false, "El precio de la vivienda supera el máximo permitido.");
        if (this.minimumFinance != null && this.minimumFinance > 1 && financeAmount < this.minimumFinance)
            return new FinanceEntityValidationResult(false, "El monto de financiamiento es menor al mínimo permitido.");
        if (this.maximumFinance != null) {
            if (this.maximumFinance > 1 && financeAmount > this.maximumFinance)
                return new FinanceEntityValidationResult(false, "El monto de financiamiento supera el máximo permitido.");
            else if (financeAmount > this.maximumFinance * housingPrice)
                return new FinanceEntityValidationResult(false, "El monto de financiamiento excede el porcentaje máximo del valor de la vivienda.");
        }
        if (this.minimumSalary != null && salary < this.minimumSalary)
            return new FinanceEntityValidationResult(false, "El salario es menor al mínimo requerido.");
        if (this.minimumDownPaymentPercentage != null && downPaymentPercentage < this.minimumDownPaymentPercentage)
            return new FinanceEntityValidationResult(false, "La cuota inicial es menor al porcentaje mínimo permitido.");
        if (this.maximumDownPaymentPercentage != null && downPaymentPercentage > this.maximumDownPaymentPercentage)
            return new FinanceEntityValidationResult(false, "La cuota inicial supera el porcentaje máximo permitido.");
        if (housingState == HousingState.EN_PROYECTO && this.inProjectMaxGracePeriodMonths != null && gracePeriodMonths > this.inProjectMaxGracePeriodMonths)
            return new FinanceEntityValidationResult(false, "El periodo de gracia excede el máximo permitido para viviendas en proyecto.");
        if (housingState != HousingState.EN_PROYECTO && this.generalMaxGracePeriodMonths != null && gracePeriodMonths > this.generalMaxGracePeriodMonths)
            return new FinanceEntityValidationResult(false, "El periodo de gracia excede el máximo permitido para viviendas en general.");
        if (isDependent) {
            if (this.minYearsDependentEmploymentTenure != null && workingYears < this.minYearsDependentEmploymentTenure)
                return new FinanceEntityValidationResult(false, "Los años de trabajo dependiente son menores al mínimo requerido.");
        } else if (this.minYearsIndependentEmploymentTenure != null && workingYears < this.minYearsIndependentEmploymentTenure)
            return new FinanceEntityValidationResult(false, "Los años de trabajo independiente son menores al mínimo requerido.");
        if (this.requiresCreditHistory && !hasCreditHistory)
            return new FinanceEntityValidationResult(false, "Se requiere historial crediticio.");
        if (!this.allowsUsedHousing && isHousingUsed)
            return new FinanceEntityValidationResult(false, "No se permite financiar vivienda usada.");
        if (!this.allowsAnotherHousingFinancing && hasAnotherHousingFinancing)
            return new FinanceEntityValidationResult(false, "No se permite tener otro financiamiento de vivienda vigente.");
        return new FinanceEntityValidationResult(true, "Cumple con todos los requisitos.");
    }

    public FinanceEntityValidationResult isFinanceEntityAccepted(EvaluateFinanceEntityCommand command) {
        if (this.minimumHousingPrice != null && command.housingPrice() < this.minimumHousingPrice)
            return new FinanceEntityValidationResult(false, "El precio de la vivienda es menor al mínimo permitido.");
        if (this.maximumHousingPrice != null && command.housingPrice() > this.maximumHousingPrice)
            return new FinanceEntityValidationResult(false, "El precio de la vivienda supera el máximo permitido.");
        if (this.minimumFinance != null && this.minimumFinance > 1 && command.financeAmount() < this.minimumFinance)
            return new FinanceEntityValidationResult(false, "El monto de financiamiento es menor al mínimo permitido.");
        if (this.maximumFinance != null) {
            if (this.maximumFinance > 1 && command.financeAmount() > this.maximumFinance)
                return new FinanceEntityValidationResult(false, "El monto de financiamiento supera el máximo permitido.");
            else if (command.financeAmount() > this.maximumFinance * command.housingPrice())
                return new FinanceEntityValidationResult(false, "El monto de financiamiento excede el porcentaje máximo del valor de la vivienda.");
        }
        if (this.minimumSalary != null && command.salary() < this.minimumSalary)
            return new FinanceEntityValidationResult(false, "El salario es menor al mínimo requerido.");
        if (this.minimumDownPaymentPercentage != null && command.downPaymentPercentage() < this.minimumDownPaymentPercentage)
            return new FinanceEntityValidationResult(false, "La cuota inicial es menor al porcentaje mínimo permitido.");
        if (this.maximumDownPaymentPercentage != null && command.downPaymentPercentage() > this.maximumDownPaymentPercentage)
            return new FinanceEntityValidationResult(false, "La cuota inicial supera el porcentaje máximo permitido.");
        if (command.housingState() == HousingState.EN_PROYECTO &&
                this.inProjectMaxGracePeriodMonths != null &&
                command.gracePeriodMonths() > this.inProjectMaxGracePeriodMonths)
            return new FinanceEntityValidationResult(false, "El periodo de gracia excede el máximo permitido para viviendas en proyecto.");
        if (command.housingState() != HousingState.EN_PROYECTO &&
                this.generalMaxGracePeriodMonths != null &&
                command.gracePeriodMonths() > this.generalMaxGracePeriodMonths)
            return new FinanceEntityValidationResult(false, "El periodo de gracia excede el máximo permitido para viviendas en general.");
        if (command.isDependent()) {
            if (this.minYearsDependentEmploymentTenure != null && command.workingYears() < this.minYearsDependentEmploymentTenure)
                return new FinanceEntityValidationResult(false, "Los años de trabajo dependiente son menores al mínimo requerido.");
        } else if (this.minYearsIndependentEmploymentTenure != null && command.workingYears() < this.minYearsIndependentEmploymentTenure)
            return new FinanceEntityValidationResult(false, "Los años de trabajo independiente son menores al mínimo requerido.");
        if (this.requiresCreditHistory && !command.hasCreditHistory())
            return new FinanceEntityValidationResult(false, "Se requiere historial crediticio.");
        if (!this.allowsUsedHousing && command.isHousingUsed())
            return new FinanceEntityValidationResult(false, "No se permite financiar vivienda usada.");
        if (!this.allowsAnotherHousingFinancing && command.hasAnotherHousingFinancing())
            return new FinanceEntityValidationResult(false, "No se permite tener otro financiamiento de vivienda vigente.");
        return new FinanceEntityValidationResult(true, "Cumple con todos los requisitos.");
    }
}
