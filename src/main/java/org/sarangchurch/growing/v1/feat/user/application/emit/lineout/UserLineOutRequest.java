package org.sarangchurch.growing.v1.feat.user.application.emit.lineout;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserLineOutRequest {
    @NotEmpty(message = "라인아웃 요청을 보내주세요.")
    @Valid
    private List<UserLineOutRequestItem> content;

    @Getter
    @NoArgsConstructor
    public static class UserLineOutRequestItem {
        @NotNull(message = "유저 id를 입력해주세요.")
        private Long userId;

        @NotNull(message = "라인아웃 날짜를 입력해주세요.")
        private LocalDate lineOutDate;

        private String reason;
    }
}
