package com.pfe.backend.service;

import java.util.List;

import com.pfe.backend.model.Service;

public interface ServiceService {
    List<Service> getAllServices();

    Service createService(Service service);

    Service getServiceById(Long id);

    Service updateService(Long id, Service service);

    void deleteService(Long id);
    List<Service> getServicesByCategorie(String nomCategorie) ;


}
