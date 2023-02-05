package com.example.application.api;

import com.example.application.dto.LoanApplicationRequestDTO;
import com.example.application.dto.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ApplicationApi", description = "Операции с проведением сделок")
@RequestMapping("/application")
public interface ApplicationApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Провести прескоринг и запросить на расчёт возможных условий кредита",
            summary = "Прескоринг и запрос на расчёт возможных условий кредита"
    )
    @ApiResponse(responseCode = "200", description = "OK", content =
    @Content(schema = @Schema(implementation = LoanOfferDTO.class)))
    List<LoanOfferDTO> createApplication(@Parameter(description = "Тело запроса", required = true)
                                         @RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO,
                                         @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Выбрать одно из предложений", summary = "Выбор одного из предложений")
    @ApiResponse(responseCode = "200", description = "OK")
    void applyOffer(@Parameter(description = "Тело запроса", required = true)
                    @RequestBody @Valid LoanOfferDTO loanOfferDTO,
                    @RequestHeader HttpHeaders headers);
}
