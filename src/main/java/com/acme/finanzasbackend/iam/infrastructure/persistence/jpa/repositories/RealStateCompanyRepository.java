package com.acme.finanzasbackend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.iam.domain.model.aggregates.RealStateCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealStateCompanyRepository extends JpaRepository<RealStateCompany, Long> {
    Optional<RealStateCompany> findById(Long id);
    boolean existsByCompanyName(String companyName);
    boolean existsByCompanyEmail(String companyEmail);
    boolean existsByUsername(String username);
    boolean existsByRuc(String ruc);
}
