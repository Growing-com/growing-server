package org.sarangchurch.growing.training.application.training.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateTrainingRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> userIds;
    private String etc;
}
