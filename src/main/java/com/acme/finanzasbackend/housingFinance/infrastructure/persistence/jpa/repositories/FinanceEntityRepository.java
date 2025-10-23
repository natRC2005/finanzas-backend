package com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceEntityRepository extends JpaRepository<FinanceEntity, Long> {
    boolean existsByName(String name);
}
