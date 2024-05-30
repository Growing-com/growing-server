package org.sarangchurch.growing.training.application.training.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.training.domain.training.TrainingType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateTrainingRequest {
    @NotNull(message = "훈련 종류를 입력해주세요.")
    private TrainingType type;

    @NotNull(message = "훈련 이름을 입력해주세요.")
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private List<Long> userIds;

    private String etc;

    @Builder
    public CreateTrainingRequest(
            TrainingType type,
            String name,
            LocalDate startDate,
            LocalDate endDate,
            List<Long> userIds,
            String etc
    ) {
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userIds = userIds;
        this.etc = etc;
    }
}
