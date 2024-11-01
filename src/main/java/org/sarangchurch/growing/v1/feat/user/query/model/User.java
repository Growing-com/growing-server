package org.sarangchurch.growing.v1.feat.user.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class User {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final LocalDate birth;

    private Long smallGroupId;

    public void setSmallGroupId(Long smallGroupId) {
        this.smallGroupId = smallGroupId;
    }
}
