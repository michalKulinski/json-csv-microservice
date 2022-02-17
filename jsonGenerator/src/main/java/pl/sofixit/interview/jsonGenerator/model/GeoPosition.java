package pl.sofixit.interview.jsonGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GeoPosition {

    private BigDecimal latitude;
    private BigDecimal longitude;
}
