package pl.sofixit.interview.jsonGenerator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pl.sofixit.interview.jsonGenerator.model.FlattenData;
import pl.sofixit.interview.jsonGenerator.model.GeneratedData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataGneneratorControllerE2E {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void httpGetGeneratedData() {
        //given
        //when
        GeneratedData[] result = testRestTemplate.getForObject("http://localhost:" + port + "/generate/json/3", GeneratedData[].class);

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    void httpGetFlattenData() {
        //given
        //when
        FlattenData[] result = testRestTemplate.getForObject("http://localhost:" + port + "/generate/json/3", FlattenData[].class);

        //then
        assertThat(result).hasSize(3);
    }
}