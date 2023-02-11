package com.example.dossier.kafka.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Message {
    private UUID applicationId;
    private String address;
    private Theme theme;
    private String text;
}
