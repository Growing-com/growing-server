package org.sarangchurch.growing.user.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.user.domain.Sex;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "성별은 필수입니다.")
    private Sex sex;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    @NotNull(message = "학년은 필수입니다.")
    @Min(value = 1, message = "학년은 1학년 미만일 수 없습니다.")
    private Integer grade;

    @NotNull(message = "재적 여부는 필수입니다.")
    private Boolean isActive;

    private LocalDate visitDate;

    private String etc;

    @Builder
    public UpdateUserRequest(String name, Sex sex, String phoneNumber, LocalDate birth, Integer grade, Boolean isActive, LocalDate visitDate, String etc) {
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.isActive = isActive;
        this.visitDate = visitDate;
        this.etc = etc;
    }
}
