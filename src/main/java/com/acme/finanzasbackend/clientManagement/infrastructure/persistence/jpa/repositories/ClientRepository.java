package com.acme.finanzasbackend.clientManagement.infrastructure.persistence.jpa.repositories;

import com.acme.finanzasbackend.clientManagement.domain.model.aggregates.Client;
import com.acme.finanzasbackend.shared.domain.model.valueobjects.RealStateCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long id);
    Boolean existsByFirstnameAndLastname(String firstName, String lastName);
    Boolean existsByDni(String dni);
    List<Client> findAllByRealStateCompanyId(RealStateCompanyId realStateCompanyId);
    Boolean existsByFirstnameAndLastnameAndId(String firstname, String lastname, Long id);
    Boolean existsByDniAndId(String dni, Long id);
}
