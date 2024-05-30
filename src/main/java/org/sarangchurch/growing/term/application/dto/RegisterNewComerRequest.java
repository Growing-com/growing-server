package org.sarangchurch.growing.term.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;
import org.sarangchurch.growing.user.domain.UserEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterNewComerRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "성별을 입력해주세요.")
    private Sex sex;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    @NotNull(message = "학년을 입력해주세요.")
    @Min(value = 1, message = "학년은 1학년 미만일 수 없습니다.")
    private Integer grade;

    @NotNull(message = "새가족반을 입력해주세요.")
    @Min(1)
    private Long teamId;

    @NotNull(message = "방문한 텀을 입력해주세요.")
    @Min(1)
    private Long visitTermId;

    @NotNull(message = "방문날짜를 입력해주세요.")
    private LocalDate visitDate;

    private String etc;

    @Builder
    public RegisterNewComerRequest(String name, Sex sex, String phoneNumber, LocalDate birth, Integer grade, Long teamId, Long visitTermId, LocalDate visitDate, String etc) {
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.teamId = teamId;
        this.visitTermId = visitTermId;
        this.visitDate = visitDate;
        this.etc = etc;
    }

    public UserEntity toEntity(String username, String password, Week week) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .name(name)
                .sex(sex)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .grade(grade)
                .role(Role.NORMAL)
                .isActive(true)
                .visitTermId(visitTermId)
                .visitDate(week.getWeek())
                .etc(etc)
                .build();
    }
}
