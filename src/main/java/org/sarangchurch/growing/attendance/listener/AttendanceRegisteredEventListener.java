package org.sarangchurch.growing.attendance.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.domain.event.AttendanceRegisteredEvent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttendanceRegisteredEventListener {

    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(AttendanceRegisteredEvent event) throws Exception {
        Job weeklyAttendanceJob = jobRegistry.getJob("weeklyAttendanceJob");

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.id", UUID.randomUUID().toString())
                .addString("requestDate", event.getWeek().toString())
                .toJobParameters();

        jobLauncher.run(weeklyAttendanceJob, jobParameters);

        log.info("[AttendanceRegisteredEventListener.on] week = {}", event.getWeek().getWeek());
    }
}
