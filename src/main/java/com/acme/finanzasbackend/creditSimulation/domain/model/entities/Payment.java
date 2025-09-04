package com.acme.finanzasbackend.creditSimulation.domain.model.entities;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Payment extends AuditableModel {
    private Integer orderNumber;
    private Double fee;
    private Double interest;
    private Double amortization;
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "credit_application_id", nullable = false)
    private CreditApplication creditApplication;

}
