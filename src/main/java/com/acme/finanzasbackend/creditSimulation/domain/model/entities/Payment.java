package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.GracePeriodType;
import com.acme.finanzasbackend.creditSimulation.domain.model.valueobjects.PeriodicCosts;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Payment extends AuditableModel {
    private Integer orderNumber;
    private LocalDate paymentDate;
    private Double tem; // Tasa Efectiva Mensual
    private GracePeriodType gracePeriodType;
    private Double initialBalance; // Saldo Inicial
    private Double interest;
    private Double fee; // Cuota
    private Double amortization;
    private PeriodicCosts periodicCosts;
    private Double finalBalance; // Saldo Final
    private Double cashFlow; // Flujo

    @ManyToOne
    @JoinColumn(name = "credit_application_id", nullable = false)
    private CreditApplication creditApplication;

    public Payment() {}

    public Payment(Integer orderNumber, LocalDate paymentDate, Double tem,
                   GracePeriodType gracePeriodType, Double initialBalance,
                   Double interest, Double fee,
                   Double amortization, PeriodicCosts periodicCosts,
                   Double finalBalance, Double cashFlow) {
        this.orderNumber = orderNumber;
        this.paymentDate = paymentDate;
        this.tem = tem;
        this.gracePeriodType = gracePeriodType;
        this.initialBalance = initialBalance;
        this.interest = interest;
        this.fee = fee;
        this.amortization = amortization;
        this.periodicCosts = periodicCosts;
        this.finalBalance = finalBalance;
        this.cashFlow = cashFlow;
    }
}
