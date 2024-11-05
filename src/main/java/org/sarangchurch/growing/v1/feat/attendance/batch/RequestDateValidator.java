package org.sarangchurch.growing.v1.feat.attendance.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class RequestDateValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if (parameters == null) {
            throw new JobParametersInvalidException("job parameter requestDate is required");
        }

        String requestDateStr = parameters.getString("requestDate");

        if (requestDateStr == null) {
            throw new JobParametersInvalidException("job parameter requestDate is required");
        }

        try {
            LocalDate.parse(requestDateStr);
        } catch (DateTimeParseException ex) {
            throw new JobParametersInvalidException("job parameter requestDate format is not valid");
        }
    }
}
