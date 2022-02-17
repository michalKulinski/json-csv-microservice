package pl.sofixit.interview.csvConverter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import pl.sofixit.interview.csvConverter.dto.FlattenData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ConverterTest {

    @InjectMocks
    Converter converter;

    @Test
    void convertTestShouldConvertFlattenDataToCsv() throws IOException {

        //given
        String size = "3";

        List<String> fields = new ArrayList<>();
        fields.add("_id");
        fields.add("country");
        fields.add("name");
        fields.add("type");

        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        converter.convert(response, getFlattenData(size), fields);

        //then
        assertTrue(response.getContentAsString().contains("Poland"));
        assertTrue(response.getContentAsString().contains("Anna"));
        assertTrue(response.getContentAsString().contains("type123"));

    }

    private List<FlattenData> getFlattenData(String size) {
        return Stream.generate(FlattenData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._id(123L)
                        .type("type123")
                        .country("Poland")
                        .name("Anna")
                        .build())
                .collect(Collectors.toList());
    }

}