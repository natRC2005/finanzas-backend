package com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusRepository extends JpaRepository<Bonus,Long> {
    Optional<Bonus> findBonusById(Long id);
}
