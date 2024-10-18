package org.sarangchurch.growing.v1.feat.user.application.dispatch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DispatchRequest {
    @NotEmpty(message = "파송 요청을 보내주세요.")
    @Valid
    private List<DispatchRequestItem> content;

    @Getter
    @NoArgsConstructor
    public static class DispatchRequestItem {
        @NotNull(message = "유저 id를 입력해주세요.")
        private Long userId;

        @NotNull(message = "파송 종류를 입력해주세요.")
        private DispatchType type;

        @NotNull(message = "파송 날짜를 입력해주세요.")
        private LocalDate sendDate;

        private LocalDate returnDate;
    }
}
