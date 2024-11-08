package org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupmember;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class AssignSmallGroupMemberRequest {
    @NotNull(message = "리더 지체 id를 입력해주세요.")
    private Long leaderUserId;

    @NotNull(message = "순원 지체 id 목록을 입력해주세요.")
    private List<Long> memberUserIds;
}
