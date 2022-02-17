package pl.sofixit.interview.jsonGenerator.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sofixit.interview.jsonGenerator.model.FlattenData;
import pl.sofixit.interview.jsonGenerator.model.GeneratedData;
import pl.sofixit.interview.jsonGenerator.model.GeoPosition;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DataGeneratorService {

    private Faker faker;

    public List<GeneratedData> generateData(String size) {
        log.info("Generating data for size: " + size);
        faker = new Faker();
        return Stream.generate(GeneratedData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._type(faker.book().genre())
                        ._id(faker.number().randomNumber())
                        .key(faker.letterify("????"))
                        .name(faker.name().lastName())
                        .fullName(faker.name().lastName() + ", " + faker.country().name())
                        .iata_airport_code(faker.letterify("????").toUpperCase())
                        .type(faker.beer().style())
                        .country(faker.country().name())
                        .geoPosition(
                                new GeoPosition(
                                        new BigDecimal(faker.numerify("##.#######")),
                                        new BigDecimal(faker.numerify("##.#######"))))
                        .location_id(faker.number().randomNumber())
                        .inEurope(faker.bool().bool())
                        .countryCode(faker.country().countryCode2())
                        .coreCountry(faker.bool().bool())
                        .distance(faker.number().randomDigit())
                        .build())
                .collect(Collectors.toList());
    }

    public List<FlattenData> generateFlattenData(String size) {
        log.info("Generating data for size: " + size);
        faker = new Faker();
        return Stream.generate(FlattenData::new)
                .limit(Integer.parseInt(size))
                .map(x -> x.builder()
                        ._type(faker.book().genre())
                        ._id(faker.number().randomNumber())
                        .key(faker.letterify("????"))
                        .name(faker.name().lastName())
                        .fullName(faker.name().lastName() + ", " + faker.country().name())
                        .iata_airport_code(faker.letterify("????").toUpperCase())
                        .type(faker.beer().style())
                        .country(faker.country().name())
                        .latitude(new BigDecimal(faker.numerify("##.#######")))
                        .longitude(new BigDecimal(faker.numerify("##.#######")))
                        .location_id(faker.number().randomNumber())
                        .inEurope(faker.bool().bool())
                        .countryCode(faker.country().countryCode2())
                        .coreCountry(faker.bool().bool())
                        .distance(faker.number().randomDigit())
                        .build())
                .collect(Collectors.toList());
    }
}
