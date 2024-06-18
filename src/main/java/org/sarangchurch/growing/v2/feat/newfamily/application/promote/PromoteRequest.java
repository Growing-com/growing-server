package org.sarangchurch.growing.v2.feat.newfamily.application.promote;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PromoteRequest {
    @NotNull(message = "등반 날짜를 입력해주세요.")
    private LocalDate promoteDate;

    private Long smallGroupId;
}
