package org.sarangchurch.growing.v2.newfamily.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RegisterNewFamilyRequest {
    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    @Min(value = 0, message = "최소 학년은 0입니다.")
    @NotNull(message = "학년을 입력해주세요.")
    private Integer grade;

    private Long newFamilyLeaderId;

    @NotNull(message = "방문 날짜를 입력해주세요.")
    private LocalDate visitDate;

    private Map<String, Object> etc;

    public NewFamily toEntity() {
        return NewFamily.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .grade(grade)
                .newFamilyLeaderId(newFamilyLeaderId)
                .visitDate(visitDate)
                .etc(etc)
                .build();
    }
}
