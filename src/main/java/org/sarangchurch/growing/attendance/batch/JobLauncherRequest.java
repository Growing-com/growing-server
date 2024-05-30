package org.sarangchurch.growing.attendance.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Properties;

@Getter
@NoArgsConstructor
public class JobLauncherRequest {
    @NotNull
    private String name;
    private Properties jobParameters;

    public JobLauncherRequest(String name, Properties jobParameters) {
        this.name = name;
        this.jobParameters = jobParameters;
    }
}
