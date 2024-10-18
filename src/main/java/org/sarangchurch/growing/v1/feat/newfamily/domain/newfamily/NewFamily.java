package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "new_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class NewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Column(name = "new_family_promote_log_id")
    private Long newFamilyPromoteLogId;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Convert(converter = NewFamilyEtcConverter.class)
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private NewFamilyEtc etc;

    public static NewFamily from(LineOutNewFamily lineoutNewFamily) {
        return NewFamily.builder()
                .userId(lineoutNewFamily.getUserId())
                .visitDate(lineoutNewFamily.getVisitDate())
                .etc(lineoutNewFamily.getEtc())
                .build();
    }

    @Builder
    public NewFamily(Long userId, Long newFamilyGroupId, LocalDate visitDate, NewFamilyEtc etc) {
        this.userId = userId;
        this.newFamilyGroupId = newFamilyGroupId;
        this.visitDate = visitDate;
        this.etc = etc;
    }

    public NewFamily setUserId(Long userId) {
        this.userId = userId;

        return this;
    }

    public void updateEtc(NewFamilyEtc etc) {
        this.etc = etc;
    }

    public void updateNewFamilyGroup(Long newFamilyGroupId) {
        this.newFamilyGroupId = newFamilyGroupId;
    }
    
    public void setPromoteLog(NewFamilyPromoteLog log) {
        if (newFamilyPromoteLogId != null) {
            throw new IllegalStateException("이미 새가족 라인업/등반 기록이 존재합니다.");
        }

        newFamilyPromoteLogId = log.getId();
    }

    public boolean hasPromoteLog() {
        return newFamilyPromoteLogId != null;
    }
}
