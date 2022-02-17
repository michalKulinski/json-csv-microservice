package pl.sofixit.interview.csvConverter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.sofixit.interview.csvConverter.dto.FlattenData;
import pl.sofixit.interview.csvConverter.model.DefaultFields;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CsvConverterService {

    private static final String FILE_NAME = "generated-data.csv";

    @Value("${jsongenerator.url}")
    private String jsonGeneratorUrl;

    @Value("${jsongenerator.port}")
    private String jsonGeneratorPort;

    @Value("${jsongenerator.scheme}")
    private String jsonGeneratorScheme;

    private RestTemplate restTemplate;

    private Converter converter;

    @Autowired
    public CsvConverterService(RestTemplate restTemplate, Converter converter) {
        this.restTemplate = restTemplate;
        this.converter = converter;
    }

    public void downloadCsv(HttpServletResponse response, String size) throws IOException {

        List<String> fields = Stream.of(DefaultFields.values())
                .map(x -> x.field)
                .collect(Collectors.toList());

        log.info("Getting generated data for size: " + size);
        ResponseEntity<List<FlattenData>> flattenData = restTemplate
                .exchange(getUri(size), HttpMethod.GET, null, new ParameterizedTypeReference<List<FlattenData>>() {
                });

        setCsvParams(response);

        log.info("Converting to csv file: " + FILE_NAME);
        converter.convert(response, flattenData.getBody(), fields);
    }


    public void downloadFiltereedCsv(HttpServletResponse response, String size, List<String> params) throws IOException {

        List<String> fields = Arrays.stream(FlattenData.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());

        log.info("Getting generated data for size: " + size);
        ResponseEntity<List<FlattenData>> flattenData = restTemplate
                .exchange(getUri(size), HttpMethod.GET, null, new ParameterizedTypeReference<List<FlattenData>>() {
                });

        List<String> headers = fields.stream().filter(params::contains).collect(Collectors.toList());

        setCsvParams(response);

        log.info("Converting to csv file: " + FILE_NAME);
        converter.convert(response, flattenData.getBody(), headers);

    }

    private String getUri(String size) {
        return UriComponentsBuilder.newInstance().scheme(jsonGeneratorScheme).host(jsonGeneratorUrl).port(jsonGeneratorPort).path("generate/json/" + size + "/flatten").build().toUriString();
    }

    private void setCsvParams(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
    }
}
