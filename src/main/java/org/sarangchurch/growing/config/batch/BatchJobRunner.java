package org.sarangchurch.growing.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobRunner {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public ExitStatus run(String name, JobParameters jobParameters) throws Exception {
        Job job = jobRegistry.getJob(name);

        return jobLauncher.run(job, jobParameters).getExitStatus();
    }
}
