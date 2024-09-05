package org.sarangchurch.growing.v1.feat.newfamily.application.requestlineup;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class V1RequestLineUpRequest {
    @NotEmpty(message = "라인업이 필요한 새가족들을 입력해주세요.")
    private List<Long> newFamilyIds;
}
