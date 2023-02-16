package com.example.deal.metric;

import com.example.deal.db.enums.ApplicationStatus;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricService {

    private static final String COUNTER_STATUS_NAME = "application.status";

    private final MeterRegistry meterRegistry;

    @PostConstruct
    private void initStatusCounters() {
        Arrays.stream(ApplicationStatus.values()).forEach(this::counterRegister);
    }

    private void counterRegister(ApplicationStatus status) {
        Counter.builder(COUNTER_STATUS_NAME)
                .description("Number of applications in each status")
                .tag("status", status.name())
                .register(meterRegistry);
    }

    public void incrementStatusCounter(ApplicationStatus status) {
        List<Tag> statusList = List.of(Tag.of("status", status.name()));
        Counter counter = meterRegistry.counter(COUNTER_STATUS_NAME, statusList);
        counter.increment();
    }
}
