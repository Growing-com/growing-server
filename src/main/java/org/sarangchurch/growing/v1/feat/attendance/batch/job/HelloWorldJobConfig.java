package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestDateValidator;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HelloWorldJobConfig {
    private static final String JOB_NAME = "helloWorldJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final RequestDateValidator requestDateValidator;

    private final Step helloWorldStep;

    @JobScope
    @Bean(name = JOB_NAME + "Parameter")
    public RequestWeekJobParameter weekJobParameter(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new RequestWeekJobParameter(requestDate);
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .validator(requestDateValidator)
                .incrementer(new RunIdIncrementer())
                .start(helloWorldStep)
                .build();
    }
}