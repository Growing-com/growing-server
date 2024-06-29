package org.sarangchurch.growing.v2.feat.newfamily.application.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Sex;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyEtc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    @NotNull(message = "성별을 입력해주세요.")
    private Sex sex;

    @Min(value = 0, message = "최소 학년은 0입니다.")
    @NotNull(message = "학년을 입력해주세요.")
    private Integer grade;

    private Long newFamilyGroupId;

    @NotNull(message = "방문 날짜를 입력해주세요.")
    private LocalDate visitDate;

    private NewFamilyEtc etc;

    public NewFamily toEntity() {
        return NewFamily.builder()
                .newFamilyGroupId(newFamilyGroupId)
                .visitDate(visitDate)
                .etc(etc)
                .build();
    }
}
