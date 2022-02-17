package pl.sofixit.interview.jsonGenerator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sofixit.interview.jsonGenerator.model.FlattenData;
import pl.sofixit.interview.jsonGenerator.model.GeneratedData;
import pl.sofixit.interview.jsonGenerator.service.DataGeneratorService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(DataGneneratorController.class)
class DataGneneratorControllerTest {

    @MockBean
    DataGeneratorService dataGeneratorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void httpGet_returnsGeneratedData() throws Exception {

        //given
        String size = "3";
        List<GeneratedData> generatedDataList = Stream.generate(GeneratedData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._id(123L)
                        ._type("_type123")
                        .country("Poland")
                        .name("Anna")
                        .build())
                .collect(Collectors.toList());

        when(dataGeneratorService.generateData(size)).thenReturn(generatedDataList);
        //when
        //then
        mockMvc.perform(get("/generate/json/" + size))
                .andExpect(content().string(containsString("\"country\":\"Poland\"")));
    }

    @Test
    void httpGet_returnsFlattenData() throws Exception {

        //given
        String size = "3";
        List<FlattenData> generatedDataList = Stream.generate(FlattenData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._id(123L)
                        ._type("_type123")
                        .country("Poland")
                        .name("Anna")
                        .build())
                .collect(Collectors.toList());

        when(dataGeneratorService.generateFlattenData(size)).thenReturn(generatedDataList);
        //when
        //then
        mockMvc.perform(get("/generate/json/" + size + "/flatten"))
                .andExpect(content().string(containsString("\"country\":\"Poland\"")));
    }

}