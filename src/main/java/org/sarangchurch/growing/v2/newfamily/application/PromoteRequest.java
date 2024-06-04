package org.sarangchurch.growing.v2.newfamily.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PromoteRequest {
    @NotNull(message = "등반 날짜를 입력해주세요.")
    private LocalDate promoteDate;

    private Long smallGroupLeaderId;
}
