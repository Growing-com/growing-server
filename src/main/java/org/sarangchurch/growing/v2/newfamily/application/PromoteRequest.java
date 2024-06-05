package org.sarangchurch.growing.v2.newfamily.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLog;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PromoteRequest {
    @NotNull(message = "등반 날짜를 입력해주세요.")
    private LocalDate promoteDate;

    private Long smallGroupId;

    public NewFamilyPromoteLog createPromoteLog() {
        if (smallGroupId != null) {
            return NewFamilyPromoteLog.builder()
                    .promoteDate(promoteDate)
                    .smallGroupId(smallGroupId)
                    .build();
        }

        return NewFamilyPromoteLog.builder()
                .promoteDate(promoteDate)
                .build();
    }
}
