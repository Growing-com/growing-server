package org.sarangchurch.growing.v1.feat.attendance.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Properties;

@Getter
@NoArgsConstructor
public class BatchJobRequest {
    @NotNull
    private String jobName;

    private Properties jobParameters;

    public BatchJobRequest(String jobName, Properties jobParameters) {
        this.jobName = jobName;
        this.jobParameters = jobParameters;
    }
}
