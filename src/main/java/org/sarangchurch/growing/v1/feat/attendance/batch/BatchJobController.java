package org.sarangchurch.growing.v1.feat.attendance.batch;


import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.config.BatchJobRunner;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BatchJobController {
    private final BatchJobRunner jobRunner;

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @PostMapping("/api/v1/run-batch-job")
    public ApiResponse<ExitStatus> launchJob(@RequestBody @Valid BatchJobRequest request) throws Exception {
        ExitStatus exitStatus = jobRunner.run(request.getJobName(), new JobParametersBuilder(request.getJobParameters())
                .addString("run.id", UUID.randomUUID().toString())
                .toJobParameters());

        return ApiResponse.of(exitStatus);
    }
}
