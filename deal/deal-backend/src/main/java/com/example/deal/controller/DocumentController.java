package com.example.deal.controller;

import com.example.deal.api.DocumentApi;
import com.example.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/deal/document")
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {

    private final DocumentService documentService;

    @Override
    public void send(UUID applicationId, HttpHeaders headers) {
        documentService.send(applicationId);
    }

    @Override
    public void sign(UUID applicationId, HttpHeaders headers) {
        documentService.sign(applicationId);
    }

    @Override
    public void code(UUID applicationId, Integer sesCode, HttpHeaders headers) {
        documentService.code(applicationId, sesCode);
    }
}
