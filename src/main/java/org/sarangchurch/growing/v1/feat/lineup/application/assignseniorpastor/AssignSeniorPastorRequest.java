package org.sarangchurch.growing.v1.feat.lineup.application.assignseniorpastor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AssignSeniorPastorRequest {
    @NotNull(message = "담당 교역자 유저 id를 입력해주세요.")
    private Long userId;
}
