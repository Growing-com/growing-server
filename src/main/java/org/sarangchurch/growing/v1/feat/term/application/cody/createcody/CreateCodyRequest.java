package org.sarangchurch.growing.v1.feat.term.application.cody.createcody;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateCodyRequest {
    @NotNull(message = "코디의 유저 id를 입력해주세요.")
    private Long codyUserId;
}
