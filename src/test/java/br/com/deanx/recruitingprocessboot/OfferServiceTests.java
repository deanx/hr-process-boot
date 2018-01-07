package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.Application;
import br.com.deanx.recruitingprocessboot.offer.Offer;
import br.com.deanx.recruitingprocessboot.offer.OfferRepository;
import br.com.deanx.recruitingprocessboot.offer.OfferService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
public class OfferServiceTests {

    @TestConfiguration
    static class OfferServiceTestContextConfiguration {

        @Bean
        public OfferService offerService() {
            return new OfferService();
        }
    }

    @Autowired
    private OfferService offerService;

    @MockBean
    private OfferRepository offerRepository;



    @Test
    public void shouldApplyToOffer() {
        Offer fakeOffer = Mockito.mock(Offer.class);
        Mockito.when(fakeOffer.getApplicationList()).thenReturn(new ArrayList<Application>());

        Application fakeApplication = Mockito.mock(Application.class);
        Mockito.when(fakeApplication.getId()).thenReturn(1L);

        offerService.applyToOffer(fakeOffer, fakeApplication);
        Assert.assertEquals(fakeOffer.getApplicationList().get(0), fakeApplication);
    }
}
