package com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GracePeriodRepository extends JpaRepository<GracePeriod, Long> {
    Optional<GracePeriod> findById(Long id);
}
