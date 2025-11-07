package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends AuditableModel {
    private Integer orderNumber;
    private Double fee;
    private Double interest;
    private Double amortization;
    private Double lifeInsuranceFee;
    private Double housingInsuranceFee;
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "credit_application_id", nullable = false)
    private CreditApplication creditApplication;

    public Payment() {}

    public Payment(Integer orderNumber, Double fee, Double interest,
                   Double amortization, Double lifeInsuranceFee,
                   Double housingInsuranceFee, Double balance) {
        this.orderNumber = orderNumber;
        this.fee = fee;
        this.interest = interest;
        this.amortization = amortization;
        this.lifeInsuranceFee = lifeInsuranceFee;
        this.housingInsuranceFee = housingInsuranceFee;
        this.balance = balance;
    }
}

// UPDATE -> Add excel table attributes