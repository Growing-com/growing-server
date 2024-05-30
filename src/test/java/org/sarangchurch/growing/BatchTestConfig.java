package org.sarangchurch.growing;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@EnableAutoConfiguration
@EnableJpaAuditing
public class BatchTestConfig {
}
