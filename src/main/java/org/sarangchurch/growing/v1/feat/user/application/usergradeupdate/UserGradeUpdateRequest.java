package org.sarangchurch.growing.v1.feat.user.application.usergradeupdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.UserGradeUpdateOption;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserGradeUpdateRequest {
    @NotNull(message = "학년 단체 변경 옵션을 선택해주세요.")
    private UserGradeUpdateOption option;
}
