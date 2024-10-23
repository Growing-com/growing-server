package org.sarangchurch.growing.v1.feat.term.application.updatecody;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateCodyRequest {
    @NotNull(message = "순모임 id 목록을 입력해주세요.")
    private List<Long> smallGroupIds;
}
