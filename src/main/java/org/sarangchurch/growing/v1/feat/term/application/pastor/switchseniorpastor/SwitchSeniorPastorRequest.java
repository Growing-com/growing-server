package org.sarangchurch.growing.v1.feat.term.application.pastor.switchseniorpastor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SwitchSeniorPastorRequest {
    @NotNull(message = "담당 교역자 id를 입력해주세요.")
    private Long targetSeniorPastorId;
}
