package br.com.deanx.recruitingprocessboot.offer;

import br.com.deanx.recruitingprocessboot.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    @Autowired
    private OfferRepository repository;

    public void applyToOffer(Offer offer, Application application) {
        offer.getApplicationList().add(application);
        repository.save(offer);
    }
}
