package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class PromotedNewFamilyListItem {
    private final Long newFamilyId;
    private final String name;
    private final Sex sex;
    private final String phoneNumber;
    private final Integer grade;
    private final String newFamilyGroupLeaderName;
    private final String smallGroupLeaderName;
    private final LocalDate promoteDate;
    private final long weeksAfterPromotion;
    @JsonIgnore
    private final Long userId;

    private long attendanceAfterPromotion;

    public PromotedNewFamilyListItem(
            Long newFamilyId,
            String name,
            Sex sex,
            String phoneNumber,
            Integer grade,
            String newFamilyGroupLeaderName,
            String smallGroupLeaderName,
            LocalDate promoteDate,
            Long userId
    ) {
        this.newFamilyId = newFamilyId;
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.newFamilyGroupLeaderName = newFamilyGroupLeaderName;
        this.smallGroupLeaderName = smallGroupLeaderName;
        this.promoteDate = promoteDate;
        // 계산
        this.weeksAfterPromotion = ChronoUnit.WEEKS.between(promoteDate, LocalDate.now());
        this.userId = userId;
    }

    public void setAttendanceAfterPromotion(long attendanceAfterPromotion) {
        this.attendanceAfterPromotion = attendanceAfterPromotion;
    }
}
