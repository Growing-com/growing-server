package org.sarangchurch.growing.term.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class RegisterTermRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotNull(message = "시작일을 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "종료일을 입력해주세요.")
    private LocalDate endDate;
    private String memo;
    private String groupings;
}
