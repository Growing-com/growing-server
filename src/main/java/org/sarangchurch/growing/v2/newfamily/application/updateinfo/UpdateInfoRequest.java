package org.sarangchurch.growing.v2.newfamily.application.updateinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UpdateInfoRequest {
    private String name;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 010-1234-1234 형식으로 입력해주세요.")
    private String phoneNumber;

    private LocalDate birth;

    private Gender gender;

    @Min(value = 0, message = "최소 학년은 0입니다.")
    private Integer grade;

    private Map<String, Object> etc;
}
