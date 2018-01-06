package br.com.deanx.recruitingprocessboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecruitingProcessBootApplication.class)
@AutoConfigureMockMvc
public class APITests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenOffers_whenGetOffers_theStatus200() throws Exception {
        this.mockMvc.perform(get("/offers")
                .accept(parseMediaType("application/json"))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );
    }


    @Test
    public void givenApplications_whenGetApplications_theStatus200() throws Exception {
        this.mockMvc.perform(get("/applications")
                .accept(parseMediaType("application/json"))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }


    @Test
    public void givenOffers_whenPostOffer_thenStatus201() throws Exception {
        mockMvc.perform(post("/offers").content("{\n" +
                "\"jobTitle\": \"Senior developer\",\n" +
                "\"startDate\": \"2012-04-23\",\n" +
                "\"numberOfApplications\":12\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
