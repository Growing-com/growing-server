package org.sarangchurch.growing.v1.feat.newfamily.application.lineout;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class LineOutRequest {
    @NotEmpty(message = "라인아웃 시킬 새가족을 입력해주세요.")
    private List<Long> newFamilyIds;
}
