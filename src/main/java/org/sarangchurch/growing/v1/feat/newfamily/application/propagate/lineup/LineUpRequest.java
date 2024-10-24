package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.lineup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LineUpRequest {
    @NotEmpty(message = "새가족 라인업 요청을 보내주세요.")
    @Valid
    private List<LineUpRequestItem> content;

    public List<Long> getNewFamilyIds() {
        return content
                .stream()
                .map(LineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());
    }

    public List<Long> getSmallGroupIds() {
        return content
                .stream()
                .map(LineUpRequestItem::getSmallGroupId)
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class LineUpRequestItem {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotNull(message = "일반 순모임 id를 입력해주세요.")
        private Long smallGroupId;
    }
}
