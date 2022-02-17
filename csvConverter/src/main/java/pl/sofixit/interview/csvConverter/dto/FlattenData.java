package pl.sofixit.interview.csvConverter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlattenData {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String _type;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private Long _id;

    @JsonProperty(required = true)
    @Size(max = 4)
    private String key;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    private String fullName;

    @JsonProperty(required = true)
    @Size(max = 4)
    private String iata_airport_code;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String type;

    @JsonProperty(required = true)
    private String country;

    @JsonProperty(required = true)
    @Positive
    private BigDecimal latitude;

    @JsonProperty(required = true)
    @Positive
    private BigDecimal longitude;

    @JsonProperty(required = true)
    private Long location_id;

    @JsonProperty(required = true)
    private Boolean inEurope;

    @JsonProperty(required = true)
    private String countryCode;

    @JsonProperty(required = true)
    private Boolean coreCountry;

    @JsonProperty(required = true)
    private Integer distance;
}