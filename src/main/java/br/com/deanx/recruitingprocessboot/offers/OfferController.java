package br.com.deanx.recruitingprocessboot.offers;

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
public class OfferController {
    @Autowired
    private OfferRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/offers")
    String listOffers() {
        return "";
    }

    @RequestMapping(method = RequestMethod.POST, value="/offers")
    ResponseEntity<Offer> saveOffer(@RequestBody Offer offer) {
        return new ResponseEntity<>(repository.save(offer), HttpStatus.CREATED);
    }
}
