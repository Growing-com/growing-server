package org.sarangchurch.growing.term.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class LineoutNewComerRequest {
    @NotNull
    private LocalDate lineoutDate;
    @NotNull
    private Integer gradeAtFirstVisit;

    public LineoutNewComerRequest(LocalDate lineoutDate, Integer gradeAtFirstVisit) {
        this.lineoutDate = lineoutDate;
        this.gradeAtFirstVisit = gradeAtFirstVisit;
    }
}
