package pl.sofixit.interview.jsonGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedData {

    private String _type;
    private Long _id;
    private String key;
    private String name;
    private String fullName;
    private String iata_airport_code;
    private String type;
    private String country;
    private GeoPosition geoPosition;
    private Long location_id;
    private Boolean inEurope;
    private String countryCode;
    private Boolean coreCountry;
    private Integer distance;
}
