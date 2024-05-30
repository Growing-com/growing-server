package org.sarangchurch.growing.attendance.batch.parameter;

import lombok.Getter;
import org.sarangchurch.growing.core.types.Week;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class RequestWeekJobParameter {
    private final Week week;

    public RequestWeekJobParameter(String requestDateStr) {
        this.week = Week.previousSunday(LocalDate.parse(requestDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
