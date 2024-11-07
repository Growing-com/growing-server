package org.sarangchurch.growing.v1.feat.term.application.createterm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateTermRequest {
    @NotNull(message = "텀 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "시작 날짜를 입력해주세요.")
    private LocalDate startDate;

    @NotNull(message = "종료 날짜를 입력해주세요.")
    private LocalDate endDate;

    public Term toEntity() {
        return Term.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
