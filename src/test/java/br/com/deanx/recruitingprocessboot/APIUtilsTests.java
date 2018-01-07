package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.Application;
import br.com.deanx.recruitingprocessboot.application.ApplicationRepository;
import br.com.deanx.recruitingprocessboot.offer.Offer;
import br.com.deanx.recruitingprocessboot.offer.OfferRepository;
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

    private Offer getFakeOffer() {
        Offer offer = new Offer();
        offer.setJobTitle("Senior developer");
        offer.setStartDate(LocalDate.now());
        offer.setApplicationList(new ArrayList<Application>());
        return offer;
    }

    public Offer createFakeOffer() {
        return offerRepository.save(getFakeOffer());
    }

    public Application createFakeApplication() {
        Offer fakeOffer = createFakeOffer();
        Application application = new Application();
        application.setEmail("test@test.com");
        application.setOffer(offerRepository.findOne(fakeOffer.getId()));
        application.setResume("Simple description");
        return applicationRepository.save(application);
    }
}
