package org.sarangchurch.growing.v1.feat.term.application.smallgroup.createsmallgroup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateSmallGroupRequest {
    @NotNull(message = "코디 id를 입력해주세요.")
    private Long codyId;

    @NotNull(message = "순장의 userId를 입력해주세요.")
    private Long leaderUserId;

    @NotNull(message = "순원의 userId 목록을 입력해주세요.")
    private List<Long> memberUserIds;
}
