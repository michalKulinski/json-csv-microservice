package pl.sofixit.interview.csvConverter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.sofixit.interview.csvConverter.service.CsvConverterService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
@Validated
public class CsvConverterController {

    CsvConverterService csvConverterService;

    @Operation(summary = "Download CSV")
    @ApiResponse(responseCode = "200", description = "Csv was successfully downloaded", content = {@Content(mediaType = "text/csv")})
    @GetMapping("/downloadCsv/{size}")
    public ResponseEntity<Void> downloadCsv(HttpServletResponse response, @Min(1) @Pattern(regexp = "^[0-9]*$", message = "Size has to be a number") @PathVariable String size) {
        try {
            csvConverterService.downloadCsv(response, size);
            log.info("Csv was successfully generated");
        } catch (IOException e) {
            log.error("Cannot download csv file: " + e.getStackTrace());
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Download CSV for filtered params")
    @ApiResponse(responseCode = "200", description = "Csv for filtered params was successfully downloaded", content = {@Content(mediaType = "text/csv")})
    @GetMapping("/downloadCsv/{size}/filter")
    public ResponseEntity<Void> downloadOrderedCsv(
            HttpServletResponse response,
            @Min(1) @Pattern(regexp = "^[0-9]*$", message = "Size has to be a number") @PathVariable String size,
            @RequestParam(name = "params") List<String> params) {
        try {
            csvConverterService.downloadFiltereedCsv(response, size, params);
            log.info("Csv was successfully generated");
        } catch (IOException e) {
            log.error("Cannot download csv file: " + e.getStackTrace());
        }
        return ResponseEntity.noContent().build();
    }
}
