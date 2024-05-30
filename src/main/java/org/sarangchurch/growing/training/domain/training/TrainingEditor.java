package org.sarangchurch.growing.training.domain.training;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TrainingEditor {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Long> userIds;
    private final String etc;

    @Builder
    public TrainingEditor(
            String name,
            LocalDate startDate,
            LocalDate endDate,
            List<Long> userIds,
            String etc
    ) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userIds = userIds;
        this.etc = etc;
    }
}
