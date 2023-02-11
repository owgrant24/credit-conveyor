package com.example.deal.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/deal/document")
@Tag(name = "DocumentApi", description = "Операции с документами")
public interface DocumentApi {

    @PostMapping(path = "/{applicationId}/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Запросить отправку документов", summary = "Запрос на отправку документов")
    @ApiResponse(responseCode = "200", description = "OK")
    void send(@PathVariable("applicationId") UUID applicationId,
              @RequestHeader HttpHeaders headers);

    @PostMapping(path = "/{applicationId}/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Запросить подписание документов", summary = "Запрос на подписание документов")
    @ApiResponse(responseCode = "200", description = "OK")
    void sign(@PathVariable("applicationId") UUID applicationId,
              @RequestHeader HttpHeaders headers);

    @PostMapping(path = "/{applicationId}/code", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Подписать документы", summary = "Подписание документов")
    @ApiResponse(responseCode = "200", description = "OK")
    void code(@PathVariable("applicationId") UUID applicationId,
              @RequestBody Integer sesCode,
              @RequestHeader HttpHeaders headers);
}
