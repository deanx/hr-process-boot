package br.com.deanx.recruitingprocessboot.offer;

import br.com.deanx.recruitingprocessboot.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository repository;

    public void applyToOffer(Offer offer, Application application) {
        List<Application> applicationList = offer.getApplicationList();
        applicationList.add(application);
        offer.setApplicationList(applicationList);
        repository.save(offer);
    }
}
