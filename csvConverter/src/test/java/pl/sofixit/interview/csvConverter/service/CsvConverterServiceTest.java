package pl.sofixit.interview.csvConverter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.sofixit.interview.csvConverter.dto.FlattenData;
import pl.sofixit.interview.csvConverter.model.DefaultFields;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvConverterServiceTest {

    @InjectMocks
    CsvConverterService csvConverterService;

    @Mock
    HttpServletResponse response;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Converter converter;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(csvConverterService, "jsonGeneratorUrl", "localhost");
        ReflectionTestUtils.setField(csvConverterService, "jsonGeneratorPort", "8084");
        ReflectionTestUtils.setField(csvConverterService, "jsonGeneratorScheme", "http");
    }

    @Test
    void downloadCsvTestShouldRunConverter() throws IOException, URISyntaxException {
        //given
        String size = "3";

        List<FlattenData> generatedDataList = getFlattenData(size);

        List<String> fields = Stream.of(DefaultFields.values())
                .map(x -> x.field)
                .collect(Collectors.toList());

        when(restTemplate
                .exchange(getUri(size), HttpMethod.GET, null, new ParameterizedTypeReference<List<FlattenData>>() {
                }))
                .thenReturn(new ResponseEntity(generatedDataList, HttpStatus.OK));

        //when
        csvConverterService.downloadCsv(response, size);

        //then
        verify(converter, times(1)).convert(response, generatedDataList, fields);
    }

    @Test
    void downloadCsvWithFilterTestShouldRunConverter() throws IOException, URISyntaxException {
        //given
        String size = "3";
        List<FlattenData> generatedDataList = getFlattenData(size);

        List<String> fields = new ArrayList<>();
        fields.add("_id");
        fields.add("type");
        fields.add("latitude");
        fields.add("longitude");

        when(restTemplate
                .exchange(getUri(size), HttpMethod.GET, null, new ParameterizedTypeReference<List<FlattenData>>() {
                }))
                .thenReturn(new ResponseEntity(generatedDataList, HttpStatus.OK));

        //when
        csvConverterService.downloadFiltereedCsv(response, size, fields);

        //then
        verify(converter, times(1)).convert(response, generatedDataList, fields);
    }

    private List<FlattenData> getFlattenData(String size) {
        return Stream.generate(FlattenData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._id(123L)
                        ._type("_type123")
                        .country("Poland")
                        .name("Anna")
                        .build())
                .collect(Collectors.toList());
    }

    private String getUri(String size) {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port("8084").path("generate/json/" + size + "/flatten").build().toUriString();
    }

}