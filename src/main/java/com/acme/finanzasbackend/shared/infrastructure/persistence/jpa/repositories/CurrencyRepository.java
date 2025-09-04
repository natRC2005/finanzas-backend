package com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.shared.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByName(String name);
}
