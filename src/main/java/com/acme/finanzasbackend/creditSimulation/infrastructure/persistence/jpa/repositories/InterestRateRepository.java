package com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
    Optional<InterestRate> findById(Long id);
}
