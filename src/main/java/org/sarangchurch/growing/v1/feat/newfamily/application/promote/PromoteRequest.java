package org.sarangchurch.growing.v1.feat.newfamily.application.promote;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PromoteRequest {
    @NotEmpty(message = "등반 요청을 보내주세요.")
    @Valid
    private List<V1PromoteRequestItem> content;

    @Getter
    @NoArgsConstructor
    public static class V1PromoteRequestItem {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotNull(message = "등반 날짜를 입력해주세요.")
        private LocalDate promoteDate;
    }
}
