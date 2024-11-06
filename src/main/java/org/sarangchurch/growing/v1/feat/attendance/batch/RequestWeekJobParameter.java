package org.sarangchurch.growing.v1.feat.attendance.batch;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class RequestWeekJobParameter {
    private final Week week;

    public RequestWeekJobParameter(String requestDateStr) {
        this.week = Week.previousSunday(LocalDate.parse(requestDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
