package org.sarangchurch.growing.attendance.batch;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.config.JobRunner;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {
    private final JobRunner jobRunner;
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/job")
    public ApiResponse<ExitStatus> launchJob(@RequestBody @Valid JobLauncherRequest request) throws Exception {
        ExitStatus exitStatus = jobRunner.run(request.getName(), new JobParametersBuilder(request.getJobParameters())
                .addString("run.id", UUID.randomUUID().toString())
                .toJobParameters());

        return ApiResponse.of(exitStatus);
    }
}
