package pl.sofixit.interview.jsonGenerator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class DataGneneratorControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void httpGet_returnsGeneratedData() throws Exception {
        //given
        int size = 3;

        //when
        //then
        mockMvc.perform(get("/generate/json/" + size))
                .andExpect(status()
                        .is2xxSuccessful());
    }

    @Test
    void httpGet_returnsFlattenData() throws Exception {
        //given
        int size = 3;

        //when
        //then
        mockMvc.perform(get("/generate/json/" + size + "/flatten"))
                .andExpect(status()
                        .is2xxSuccessful());
    }

    @Test
    void httpGet_returnsBadRequestForSizeAsString() throws Exception {
        //given
        String size = "dummy";

        //when
        //then
        mockMvc.perform(get("/generate/json/" + size))
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    void httpGet_returnsBadRequestForSizeAsNegative() throws Exception {
        //given
        int size = -1;

        //when
        //then
        mockMvc.perform(get("/generate/json/" + size))
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    void httpGet_returnsBadRequestForSizeAsZero() throws Exception {
        //given
        int size = 0;

        //when
        //then
        mockMvc.perform(get("/generate/json/" + size))
                .andExpect(status()
                        .isBadRequest());
    }
}