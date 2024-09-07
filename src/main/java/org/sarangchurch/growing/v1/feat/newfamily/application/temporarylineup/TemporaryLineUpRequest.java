package org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class TemporaryLineUpRequest {
    @NotEmpty(message = "후보순장 임시 라인업 요청을 보내주세요.")
    @Valid
    private List<V1TemporaryLineUpRequestItem> content;

    @Getter
    @NoArgsConstructor
    public static class V1TemporaryLineUpRequestItem {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotEmpty(message = "후보 순장 목록을 입력해주세요.")
        private List<Long> temporarySmallGroupIds;
    }
}
