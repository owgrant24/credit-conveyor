package com.example.deal.db.helper;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.StatusHistory;
import com.example.deal.db.enums.ApplicationStatus;
import com.example.deal.db.enums.ChangeType;
import com.example.deal.db.repository.ApplicationRepository;
import com.example.deal.metric.MetricMonitoring;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApplicationHelper {

    private final ApplicationRepository applicationRepository;

    public Application getById(UUID id) {
        return applicationRepository.getReferenceById(id);
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @MetricMonitoring
    public Application saveAndUpdateStatus(Application application, ApplicationStatus status, ChangeType changeType) {
        updateStatus(application, status, changeType);
        return applicationRepository.save(application);
    }

    private void updateStatus(Application application, ApplicationStatus status, ChangeType changeType) {
        List<StatusHistory> statusHistory = updateStatusHistories(application.getStatusHistory(), status, changeType);
        application.setStatus(status);
        application.setStatusHistory(statusHistory);
    }

    private static List<StatusHistory> updateStatusHistories(List<StatusHistory> statusHistory,
                                                             ApplicationStatus status,
                                                             ChangeType changeType) {
        if (Objects.isNull(statusHistory)) {
            statusHistory = new ArrayList<>();
        }

        StatusHistory history = createStatusHistory(status, changeType);
        statusHistory.add(history);
        return statusHistory;
    }

    private static StatusHistory createStatusHistory(ApplicationStatus status, ChangeType changeType) {
        StatusHistory history = new StatusHistory();
        history.setStatus(status);
        history.setChangeType(changeType);
        history.setTime(Timestamp.from(Instant.now()));
        return history;
    }
}
