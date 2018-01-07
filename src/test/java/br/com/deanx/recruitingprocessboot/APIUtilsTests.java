package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.Application;
import br.com.deanx.recruitingprocessboot.application.ApplicationRepository;
import br.com.deanx.recruitingprocessboot.offers.Offer;
import br.com.deanx.recruitingprocessboot.offers.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class APIUtilsTests {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Offer createFakeOffer() {
        Offer offer = new Offer();
        offer.setJobTitle("Senior developer");
        offer.setStartDate(LocalDate.now());
        offer.setApplicationList(new ArrayList<Application>());
        Offer newOffer = offerRepository.save(offer);
        return newOffer;
    }

    public Application createFakeApplication() {
        Application application = new Application();
        application.setEmail("test@test.com");
        return applicationRepository.save(application);
    }
}
