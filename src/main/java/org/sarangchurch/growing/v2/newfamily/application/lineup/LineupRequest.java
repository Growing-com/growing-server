package org.sarangchurch.growing.v2.newfamily.application.lineup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LineupRequest {
    @NotNull(message = "라인업할 순모임을 입력해주세요.")
    private Long smallGroupId;

    private LocalDate promoteDate;
}
