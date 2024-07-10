package org.sarangchurch.growing.v2.feat.newfamily.application.batchlneup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class BatchLineupRequest {
    @NotEmpty(message = "라인업 요청을 입력해주세요.")
    @Valid
    private List<LineupRequest> requests;

    @Getter
    @NoArgsConstructor
    public static class LineupRequest {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;

        @NotNull(message = "라인업할 순모임을 입력해주세요.")
        private Long smallGroupId;

        private LocalDate promoteDate;
    }
}
