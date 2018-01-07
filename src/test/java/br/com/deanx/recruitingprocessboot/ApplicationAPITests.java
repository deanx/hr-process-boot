package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.Application;
import br.com.deanx.recruitingprocessboot.application.ApplicationRepository;
import br.com.deanx.recruitingprocessboot.offers.Offer;
import br.com.deanx.recruitingprocessboot.offers.OfferRepository;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecruitingProcessBootApplication.class)
@AutoConfigureMockMvc
public class ApplicationAPITests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private APIUtilsTests apiUtilsTests;
    @Before
    public void setUp() {
        offerRepository.deleteAll();
    }

    @Test
    public void givenApplications_whenGetApplicationById_theStatus200() throws Exception {
        Application fakeApplication = apiUtilsTests.createFakeApplication();
        this.mockMvc.perform(get("/applications/" + fakeApplication.getId())
                .accept(parseMediaType("application/json"))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(fakeApplication.getId().intValue())));
    }

    @Test
    public void givenApplications_whenPostApplication_thenStatus201() throws Exception {
        Offer fakeOffer = apiUtilsTests.createFakeOffer();
        Long newOfferId = fakeOffer.getId();

        mockMvc.perform(post("/applications").content("{\n" +
                "    \"offer\": {\n" +
                "        \"id\": " + newOfferId + "\n" +
                "    },\n" +
                "    \"email\": \"jane@doe.com\",\n" +
                "    \"resume\": \"Thats a text\",\n" +
                "    \"applicationStatus\": \"HIRED\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenApplications_whenPutApplicationStatus_thenStatus200() throws Exception {
        Application fakeApplication = apiUtilsTests.createFakeApplication();
        mockMvc.perform(put("/applications/" + fakeApplication.getId() + "/applicationStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"INVITED\""))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.applicationStatus", is("INVITED")));

        Application updatedApplication = applicationRepository.findOne(fakeApplication.getId());
        Assert.assertEquals("INVITED", updatedApplication.getApplicationStatus().toString());

    }

}
