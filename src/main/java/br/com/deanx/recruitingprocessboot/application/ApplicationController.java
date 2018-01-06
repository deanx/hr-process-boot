package br.com.deanx.recruitingprocessboot.application;

import br.com.deanx.recruitingprocessboot.offers.Offer;
import br.com.deanx.recruitingprocessboot.offers.OfferRepository;
import br.com.deanx.recruitingprocessboot.offers.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ApplicationController {
    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @RequestMapping(method = RequestMethod.POST, value="/applications")
    ResponseEntity saveOffer(@RequestBody Application application) {
        Offer offer;
        try {
            offer = offerRepository.findById(application.getOffer().getId()).get(0);
        } catch(NullPointerException npe) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        offer.setNumberOfApplications(offer.getNumberOfApplications()+1);
        application.setOffer(offer);
        application = repository.save(application);
        offerService.applyToOffer(offer, application);

        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }
}
