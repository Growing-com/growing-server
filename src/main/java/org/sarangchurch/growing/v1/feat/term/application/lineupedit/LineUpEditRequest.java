package org.sarangchurch.growing.v1.feat.term.application.lineupedit;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class LineUpEditRequest {
    @NotNull(message = "순원 id를 입력해주세요.")
    private Long smallGroupMemberId;

    @NotNull(message = "이동할 순모임 id를 입력해주세요.")
    private Long targetSmallGroupId;
}
