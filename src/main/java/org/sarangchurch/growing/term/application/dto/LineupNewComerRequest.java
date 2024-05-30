package org.sarangchurch.growing.term.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class LineupNewComerRequest {
    @NotNull
    private Long plantTeamId;
    @NotNull
    private LocalDate lineupDate;
    @NotNull
    private Integer gradeAtFirstVisit;

    public LineupNewComerRequest(Long plantTeamId, LocalDate lineupDate, Integer gradeAtFirstVisit) {
        this.plantTeamId = plantTeamId;
        this.lineupDate = lineupDate;
        this.gradeAtFirstVisit = gradeAtFirstVisit;
    }
}
