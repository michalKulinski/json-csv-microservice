package pl.sofixit.interview.jsonGenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sofixit.interview.jsonGenerator.model.FlattenData;
import pl.sofixit.interview.jsonGenerator.model.GeneratedData;
import pl.sofixit.interview.jsonGenerator.service.DataGeneratorService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
@AllArgsConstructor
@Validated
public class DataGneneratorController {

    private DataGeneratorService dataGeneratorService;

    @Operation(summary = "Generate predefined number of data")
    @ApiResponse(responseCode = "200", description = "Data were generated",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = GeneratedData.class))})
    @GetMapping("generate/json/{size}")
    public ResponseEntity<List<GeneratedData>> generateJson(@Min(1) @Pattern(regexp = "^[0-9]*$", message = "Size has to be a number") @PathVariable String size) {
        return ResponseEntity.ok().body(dataGeneratorService.generateData(size));
    }

    @Operation(summary = "Generate predefined number of data in flatten structure")
    @ApiResponse(responseCode = "200", description = "Flatten data were generated",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = FlattenData.class))})
    @GetMapping("generate/json/{size}/flatten")
    public ResponseEntity<List<FlattenData>> generateFlattenData(@Min(1) @Pattern(regexp = "^[0-9]*$", message = "Size has to be a number") @PathVariable String size) {
        return ResponseEntity.ok().body(dataGeneratorService.generateFlattenData(size));
    }
}
