package org.sarangchurch.growing.v1.feat.user.application.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserRegisterRequest {
    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "성별을 입력해주세요.")
    private Sex sex;

    @NotNull(message = "번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    @Min(value = 0, message = "최소 학년은 0입니다.")
    @NotNull(message = "학년을 입력해주세요.")
    private Integer grade;

    private Long smallGroupId;
}
