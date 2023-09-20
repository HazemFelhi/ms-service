package com.pfe.backend.controller;

import java.util.List;

import com.pfe.backend.exception.ServiceNotFoundException;
import com.pfe.backend.exception.ServiceProcessingException;
import com.pfe.backend.model.Service;
import com.pfe.backend.service.ServiceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/services")
public class ServiceController {
    private static final Logger log = LoggerFactory.getLogger(ServiceController.class);

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            Service createdService = serviceService.createService(service);
            return ResponseEntity.ok(createdService);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la création du service : ", e);
            throw new ServiceProcessingException("Une erreur s'est produite lors de la création du service.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> allServices = serviceService.getAllServices();
            return ResponseEntity.ok(allServices);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la récupération des services : ", e);
            throw new ServiceProcessingException("Une erreur s'est produite lors de la récupération des services.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        try {
            Service service = serviceService.getServiceById(id);
            if (service == null) {
                throw new ServiceNotFoundException("Le service avec l'ID " + id + " n'a pas été trouvé.");
            }
            return ResponseEntity.ok(service);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la récupération du service : ", e);
            throw new ServiceProcessingException("Une erreur s'est produite lors de la récupération du service.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service service) {
        try {
            Service updatedService = serviceService.updateService(id, service);
            return ResponseEntity.ok(updatedService);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la mise à jour du service : ", e);
            throw new ServiceProcessingException("Une erreur s'est produite lors de la mise à jour du service.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        try {
            serviceService.deleteService(id);
            return ResponseEntity.ok("Le service avec l'ID " + id + " a été supprimé avec succès.");
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression du service : ", e);
            throw new ServiceProcessingException("Une erreur s'est produite lors de la suppression du service.");
        }
    }


    @GetMapping("/categorie/{nomCategorie}")
    public List<Service> getServicesByCategorie(@PathVariable("nomCategorie") String nomCategorie) {
        return serviceService.getServicesByCategorie(nomCategorie);
    }




    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<String> handleServiceNotFoundException(ServiceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ServiceProcessingException.class)
    public ResponseEntity<String> handleServiceProcessingException(ServiceProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
