package br.com.deanx.recruitingprocessboot.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class OfferController {
    @Autowired
    private OfferRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/offers")
    ResponseEntity<List<Offer>> listAll(){
        return new ResponseEntity<>((List<Offer>)repository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/offers/{offerId}")
    public ResponseEntity findOffer(@PathVariable Long offerId) {
        List<Offer> offers = repository.findById(offerId);
        return 1 == offers.size() ? new ResponseEntity<>(offers.get(0), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value="/offers")
    ResponseEntity<Offer> saveOffer(@RequestBody Offer offer) {
        return new ResponseEntity<>(repository.save(offer), HttpStatus.CREATED);
    }


}
