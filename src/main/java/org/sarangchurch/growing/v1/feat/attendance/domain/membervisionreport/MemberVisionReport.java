package org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member_vision_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Builder
    public MemberVisionReport(LocalDate date, Long userId, Long smallGroupId, Long newFamilyGroupId, Long codyId) {
        this.date = date;
        this.userId = userId;
        this.smallGroupId = smallGroupId;
        this.newFamilyGroupId = newFamilyGroupId;
        this.codyId = codyId;
    }
}
