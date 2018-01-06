package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.offers.Offer;
import br.com.deanx.recruitingprocessboot.offers.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class APIUtilsTests {
    @Autowired
    private OfferRepository offerRepository;
    public Offer createFakeOffer() {
        Offer offer = new Offer();
        offer.setJobTitle("Senior developer");
        offer.setStartDate(LocalDate.now());
        offer.setNumberOfApplications(1);

        Offer newOffer = offerRepository.save(offer);
        return newOffer;
    }
}
