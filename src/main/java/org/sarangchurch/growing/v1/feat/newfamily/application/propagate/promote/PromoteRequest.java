package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.promote;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PromoteRequest {
    @NotEmpty(message = "등반 요청을 보내주세요.")
    @Valid
    private List<PromoteRequestItem> content;

    public List<Long> getNewFamilyIds() {
        return content
                .stream()
                .map(PromoteRequestItem::getNewFamilyId)
                .collect(Collectors.toList());
    }

    public List<LocalDate> getPromoteDates() {
        return content
                .stream()
                .map(PromoteRequest.PromoteRequestItem::getPromoteDate)
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class PromoteRequestItem {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotNull(message = "등반 날짜를 입력해주세요.")
        private LocalDate promoteDate;
    }
}
