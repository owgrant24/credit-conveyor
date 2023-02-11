package com.example.conveyor.api;

import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.dto.response.CreditDTO;
import com.example.conveyor.dto.response.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ConveyorApi", description = "Операции с кредитом")
public interface ConveyorApi {

    @PostMapping(path = "/conveyor/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Расчитать возможные условия кредита", summary = "Расчёт возможных условий кредита")
    @ApiResponse(responseCode = "200", description = "OK", content =
    @Content(schema = @Schema(implementation = LoanOfferDTO.class)))
    List<LoanOfferDTO> offers(@Parameter(description = "Тело запроса", required = true)
                              @RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO,
                              @RequestHeader HttpHeaders headers);

    @PostMapping(path = "/conveyor/calculation", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Расчитать полные условия кредита", summary = "Полный расчет параметров кредита")
    @ApiResponse(responseCode = "200", description = "OK", content =
    @Content(schema = @Schema(implementation = CreditDTO.class)))
    CreditDTO calculation(@Parameter(description = "Тело запроса", required = true)
                          @RequestBody @Valid ScoringDataDTO scoringDataDTO,
                          @RequestHeader HttpHeaders headers);
}
