package pl.sofixit.interview.csvConverter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import pl.sofixit.interview.csvConverter.service.CsvConverterService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CsvConverterController.class)
class CsvConverterControllerTest {

    @MockBean
    CsvConverterService csvConverterService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void httpGet_shouldRunCsvConverterService() throws Exception {

        //given
        String size = "3";

        //when
        //then
        MockHttpServletResponse response = mockMvc
                .perform(get("/downloadCsv/" + size))
                .andExpect(status()
                        .is2xxSuccessful())
                .andExpect(status()
                        .isNoContent())
                .andReturn()
                .getResponse();

        verify(csvConverterService, times(1)).downloadCsv(response, size);
    }

    @Test
    void httpGet_shouldRunCsvConverterServiceWithParameters() throws Exception {

        //given
        String size = "3";

        List<String> params = new ArrayList<>();
        params.add("_id");
        params.add("type");
        params.add("latitude");
        params.add("longitude");

        params.stream().map(x->x.join(",")).collect(Collectors.joining());

        //when
        //then
        MockHttpServletResponse response = mockMvc
                .perform(get("/downloadCsv/" + size + "/filter")
                        .param("params", params.stream().collect(Collectors.joining(","))))
                .andExpect(status()
                        .is2xxSuccessful())
                .andExpect(status()
                        .isNoContent())
                .andReturn()
                .getResponse();

        verify(csvConverterService, times(1)).downloadFiltereedCsv(response, size, params);
    }

    @Test
    void httpGet_shouldReturnBadRequestDueToBadSize() throws Exception {

        //given
        String size = "0";

        List<String> params = new ArrayList<>();
        params.add("_id");
        params.add("type");
        params.add("latitude");
        params.add("longitude");

        params.stream().map(x->x.join(",")).collect(Collectors.joining());

        //when
        //then
        MockHttpServletResponse response = mockMvc
                .perform(get("/downloadCsv/" + size + "/filter")
                        .param("params", params.stream().collect(Collectors.joining(","))))
                .andExpect(status()
                        .isBadRequest())
                .andExpect(content()
                        .string("Request is not valid due to validation error: downloadOrderedCsv.size: must be greater than or equal to 1"))
                .andReturn()
                .getResponse();

        verify(csvConverterService, times(0)).downloadFiltereedCsv(response, size, params);
    }

}