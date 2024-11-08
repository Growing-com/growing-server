package org.sarangchurch.growing.v1.feat.lineup.application.assigncody;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class AssignCodyRequest {
    @NotNull(message = "코디 지체 id 목록을 입력해주세요.")
    private List<Long> userIds;
}
