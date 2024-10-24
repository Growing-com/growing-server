package org.sarangchurch.growing.v1.feat.user.application.emit.lineout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> getUserIds() {
        return content.stream()
                .map(UserLineOutRequest.UserLineOutRequestItem::getUserId)
                .collect(Collectors.toList());
    }

    public List<LineOutUser> toLineOutUsers() {
        return content.stream()
                .map(it ->
                        LineOutUser.builder()
                                .userId(it.getUserId())
                                .lineOutDate(it.getLineOutDate())
                                .reason(it.getReason())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
