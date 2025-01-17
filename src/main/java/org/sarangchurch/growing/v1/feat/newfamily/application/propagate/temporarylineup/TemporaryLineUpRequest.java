package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.temporarylineup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class TemporaryLineUpRequest {
    @NotEmpty(message = "후보순장 임시 라인업 요청을 보내주세요.")
    @Valid
    private List<TemporaryLineUpRequestItem> content;

    public List<Long> getNewFamilyIds() {
        return content.stream()
                .map(TemporaryLineUpRequest.TemporaryLineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());
    }

    public List<List<Long>> getTemporarySmallGroupIds() {
        return content.stream()
                .map(TemporaryLineUpRequest.TemporaryLineUpRequestItem::getTemporarySmallGroupIds)
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class TemporaryLineUpRequestItem {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotEmpty(message = "후보 순장 목록을 입력해주세요.")
        private List<Long> temporarySmallGroupIds;
    }
}
