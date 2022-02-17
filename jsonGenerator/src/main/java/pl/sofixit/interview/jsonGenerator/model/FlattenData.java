package pl.sofixit.interview.jsonGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlattenData {

    private String _type;
    private Long _id;
    private String key;
    private String name;
    private String fullName;
    private String iata_airport_code;
    private String type;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long location_id;
    private Boolean inEurope;
    private String countryCode;
    private Boolean coreCountry;
    private Integer distance;
}
