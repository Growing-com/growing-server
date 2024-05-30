package org.sarangchurch.growing.attendance.query.attendance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AttendanceSearchCond {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endDate;
    private final String name;
    private final Integer grade;
    private final List<Long> codyId;
    private final Boolean isNewOnly;

    public void validateParams() {
        this.validateDateRange();
        this.validateParamExists();
    }

    private void validateDateRange() {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("날짜의 범위가 잘못되었습니다.");
        }
    }

    private void validateParamExists() {
        if (name == null && grade == null && codyId == null && isNewOnly == null) {
            throw new IllegalArgumentException("검색 조건을 입력해주세요.");
        }
    }
}
