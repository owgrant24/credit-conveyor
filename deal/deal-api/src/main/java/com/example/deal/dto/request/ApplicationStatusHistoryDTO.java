package com.example.deal.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationStatusHistoryDTO {
    private LocalDateTime time;
    private StatusDTO status;
    private ChangeTypeDTO changeType;

    public enum StatusDTO {
        CALCULATED,
        ISSUED
    }

    public enum ChangeTypeDTO {
        AUTOMATIC,
        MANUAL
    }
}
