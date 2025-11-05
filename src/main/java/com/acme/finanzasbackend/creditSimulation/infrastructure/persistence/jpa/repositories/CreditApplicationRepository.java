package com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
    Optional<CreditApplication> findById(Long id);
    Boolean existsByClient(Client client);
}
