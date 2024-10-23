package org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.updatenewfamilygroup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateNewFamilyGroupRequest {
    @NotNull(message = "순원의 userId 리스트를 입력해주세요.")
    private List<Long> memberUserIds;
}
