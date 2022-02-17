package pl.sofixit.interview.csvConverter.service;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.sofixit.interview.csvConverter.dto.FlattenData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class Converter {

    public CsvMapper convert(HttpServletResponse servletResponse, List<FlattenData> flattenDataList, List<String> params) throws IOException {

        var mapper = new ObjectMapper().addMixIn(FlattenData.class, DynamicMixIn.class);
        var filterProvider = new SimpleFilterProvider()
                .addFilter("dynamicFilter", SimpleBeanPropertyFilter.filterOutAllExcept(params.toArray(String[]::new)));
        mapper.setFilterProvider(filterProvider);

        JsonNode jsonTree = mapper.convertValue(flattenDataList, JsonNode.class);

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(servletResponse.getWriter(), jsonTree);

        return csvMapper;
    }

    @JsonFilter("dynamicFilter")
    class DynamicMixIn {
    }
}
