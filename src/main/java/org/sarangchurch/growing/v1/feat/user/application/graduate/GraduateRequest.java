package org.sarangchurch.growing.v1.feat.user.application.graduate;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class GraduateRequest {
    @NotEmpty(message = "유저 id 목록을 입력해주세요.")
    private List<Long> userIds;

    @NotNull(message = "졸업날짜를 입력해주세요.")
    private LocalDate graduateDate;
}
