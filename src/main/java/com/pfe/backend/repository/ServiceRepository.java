
package com.pfe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfe.backend.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByNomService(String nomService);
    List<Service> findByNomCategorie(String nomCategorie);

}
