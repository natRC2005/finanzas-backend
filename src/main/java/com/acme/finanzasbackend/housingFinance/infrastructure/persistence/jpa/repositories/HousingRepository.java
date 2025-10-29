package com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.Housing;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HousingRepository extends JpaRepository<Housing, Long> {
    Optional<Housing> findById(Long id);
    boolean existsByTitle(String title);
    List<Housing> findByRealStateCompanyId(RealStateCompanyId realStateCompanyId);
}
