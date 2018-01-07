package br.com.deanx.recruitingprocessboot.application;

import br.com.deanx.recruitingprocessboot.offer.Offer;
import br.com.deanx.recruitingprocessboot.offer.OfferRepository;
import br.com.deanx.recruitingprocessboot.offer.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;

@RestController
@EnableAutoConfiguration
public class ApplicationController {
    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CountDownLatch latch;

    @Autowired
    EventBus eventBus;

    @Autowired
    ApplicationStatusNotificationService applicationStatusNotificationService;

    @PostMapping("/applications")
    ResponseEntity saveOffer(@RequestBody Application application) {
        Offer offer;
        try {
            offer = offerRepository.findById(application.getOffer().getId()).get(0);
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        application.setOffer(offer);
        application = repository.save(application);
        try {
            offerService.applyToOffer(offer, application);
        } catch(Exception e) {
            // if there'' any trying to associate offer to application, rollback application without offer
            application.setOffer(null);
            repository.save(application);
        }

        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/applications/{applicationId}")
    ResponseEntity findApplication(@PathVariable Long applicationId) {
        Application application = repository.findOne(applicationId);
        return null != application ? new ResponseEntity<>(application, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/applications/{applicationId}/applicationStatus")
    ResponseEntity updateApplicationStatus(@RequestBody String applicationStatus, @PathVariable Long applicationId) throws InterruptedException {
        Application application = repository.findOne(applicationId);
        if(null == application) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        final Application.applicationStatus newStatus = Application.applicationStatus.valueOf(applicationStatus.substring(1, applicationStatus.length() -1));
        application.setApplicationStatus(newStatus);

        repository.save(application);
        applicationStatusNotificationService.notify(application);

        return new ResponseEntity(application, HttpStatus.OK);
    }

}
