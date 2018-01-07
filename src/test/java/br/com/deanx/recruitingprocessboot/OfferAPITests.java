package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.Application;
import br.com.deanx.recruitingprocessboot.application.ApplicationRepository;
import br.com.deanx.recruitingprocessboot.offers.Offer;
import br.com.deanx.recruitingprocessboot.offers.OfferRepository;
import br.com.deanx.recruitingprocessboot.offers.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecruitingProcessBootApplication.class)
@AutoConfigureMockMvc
public class OfferAPITests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private APIUtilsTests apiUtilsTests;

    @Autowired
    private OfferService offerService;

    @Before
    public void setUp() {
        offerRepository.deleteAll();
    }
    @Test
    public void givenOffers_whenGetOffers_theStatus200() throws Exception {
        this.mockMvc.perform(get("/offers/")
                .accept(parseMediaType("application/json"))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    public void givenOffers_whenPostOffer_thenStatus201() throws Exception {
        mockMvc.perform(post("/offers/").content("{\n" +
                "\"jobTitle\": \"Senior developer\",\n" +
                "\"startDate\": \"2012-04-23\",\n" +
                "\"numberOfApplications\":12\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenOffers_whenGetOfferById_thenStatus200() throws Exception {
        Offer fakeOffer = apiUtilsTests.createFakeOffer();
        Long newOfferId = fakeOffer.getId();
        mockMvc.perform(get("/offers/" + newOfferId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jobTitle",is("Senior developer")));

    }

    @Test
    public void givenOffers_whenGetApplicationsByOfferById_thenStatus200() throws Exception {
        Application fakeApplication = apiUtilsTests.createFakeApplication();
        Offer fakeOffer = apiUtilsTests.createFakeOffer();
        offerService.applyToOffer(fakeOffer, fakeApplication);

        mockMvc.perform(get("/offers/" + fakeOffer.getId() + "/applications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(fakeApplication.getId().intValue())));
    }

    @Test
    public void givenSpecificOffer_whenGetOfferById_thenCountApplicationsAndStatus200() throws Exception {
        Application fakeApplication = apiUtilsTests.createFakeApplication();
        Offer fakeOffer = apiUtilsTests.createFakeOffer();
        offerService.applyToOffer(fakeOffer, fakeApplication);
        mockMvc.perform(get("/offers/" + fakeOffer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfApplications", is(1)));
    }

}