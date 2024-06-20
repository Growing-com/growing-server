package org.sarangchurch.growing.v2.feat.newfamily.application.assign;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AssignNewFamilyGroupRequest {
    @NotNull(message = "배정할 새가족반을 입력해주세요.")
    private Long newFamilyGroupId;
}
