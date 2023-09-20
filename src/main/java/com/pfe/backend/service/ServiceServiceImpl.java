package com.pfe.backend.service;

import com.pfe.backend.repository.ServiceRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfe.backend.model.Service;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Service createService(Service service) {
        // Vérifier si un service avec le même nom existe déjà
        if (serviceRepository.existsByNomService(service.getNomService())) {
            throw new IllegalArgumentException("A service with the same name already exists");
        }
        return serviceRepository.save(service);
    }

    @Override
    public Service getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service with id " + id + " not found"));
    }

    @Override
    public Service updateService(Long id, Service service) {
        Service existingService = getServiceById(id);
        existingService.setNomService(service.getNomService());
        return serviceRepository.save(existingService);
    }

    @Override
    public void deleteService(Long id) {
        Service existingService = getServiceById(id);
        serviceRepository.delete(existingService);
    }





    @Override
    public List<Service> getServicesByCategorie(String nomCategorie) {
        return serviceRepository.findByNomCategorie(nomCategorie);
    }

}