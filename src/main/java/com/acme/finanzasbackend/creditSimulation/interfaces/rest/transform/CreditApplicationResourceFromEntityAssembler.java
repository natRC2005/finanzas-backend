package com.acme.finanzasbackend.creditSimulation.interfaces.rest.transform;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.interfaces.rest.resources.CreditApplicationResource;

import java.util.stream.Collectors;

public class CreditApplicationResourceFromEntityAssembler {
    public static CreditApplicationResource toResourceFromEntity(CreditApplication entity) {
        return new CreditApplicationResource(
                entity.getId(),
                entity.getRealStateCompanyId().realStateCompanyId(),
                entity.getStartDate(),
                entity.getClient().getId(),
                entity.getHousing().getId(),
                entity.getCurrency().getId(),
                entity.getFinanceEntity().getId(),
                entity.getFinanceEntityApproved().accepted(),
                entity.getFinanceEntityApproved().reason(),
                InterestRateResourceFromEntityAssembler.toResourceFromEntity(entity.getInterestRate()),
                entity.getMonthlyLifeInsuranceRate(),
                entity.getMonthlyHousingInsuranceRate(),
                entity.getMonthsPaymentTerm(),
                entity.getDownPaymentPercentage(),
                entity.getFinancing(),
                entity.getTceaPercentage(),
                entity.getVan() != null ? entity.getVan().van() : null,
                entity.getTir() != null ? entity.getTir().tir() : null,
                BonusResourceFromEntityAssembler.toResourceFromEntity(entity.getBonus()),
                GracePeriodResourceFromEntityAssembler.toResourceFromEntity(entity.getGracePeriod()),
                entity.getPayments() != null
                        ? entity.getPayments().stream()
                        .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                        .collect(Collectors.toList())
                        : null
        );
    }
}
