package com.example.deal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmailMessage {
    private UUID applicationId;
    private String address;
    private Theme theme;
}
