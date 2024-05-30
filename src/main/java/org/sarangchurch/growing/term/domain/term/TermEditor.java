package org.sarangchurch.growing.term.domain.term;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TermEditor {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String memo;
    private final String groupings;

    @Builder
    public TermEditor(String name, LocalDate startDate, LocalDate endDate, String memo, String groupings) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memo = memo;
        this.groupings = groupings;
    }
}
