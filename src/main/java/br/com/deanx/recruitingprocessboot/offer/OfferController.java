package br.com.deanx.recruitingprocessboot.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class OfferController {
    @Autowired
    private OfferRepository repository;

    @GetMapping("/offers")
    ResponseEntity<List<Offer>> listAll(){
        return new ResponseEntity<>((List<Offer>)repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity findOffer(@PathVariable Long offerId) {
        Offer offer = repository.findOne(offerId);
        return null != offer ? new ResponseEntity<>(offer, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/offers")
    ResponseEntity<Offer> saveOffer(@Valid @RequestBody Offer offer) {
        return new ResponseEntity<>(repository.save(offer), HttpStatus.CREATED);
    }

    @GetMapping("/offers/{offerId}/applications")
    ResponseEntity getApplicationsByOfferId(@PathVariable Long offerId) {
        Offer offer = repository.findOne(offerId);
        return null != offer ? new ResponseEntity<>(offer.getApplicationList(), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
