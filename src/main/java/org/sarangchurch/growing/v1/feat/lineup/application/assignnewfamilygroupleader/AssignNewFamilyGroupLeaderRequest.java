package org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupleader;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class AssignNewFamilyGroupLeaderRequest {
    @NotNull(message = "코디 지체 id를 입력해주세요.")
    private Long codyUserId;

    @NotNull(message = "새가족 순장 지체 id 목록을 입력해주세요.")
    private List<Long> newFamilyGroupLeaderUserIds;
}
