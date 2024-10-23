package org.sarangchurch.growing.v1.feat.term.application.pastor.createpastor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreatePastorRequest {
    @NotNull(message = "교역자의 유저id를 입력해주세요.")
    private Long pastorUserId;
}
