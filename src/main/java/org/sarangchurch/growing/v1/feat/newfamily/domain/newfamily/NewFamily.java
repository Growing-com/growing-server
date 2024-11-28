package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.config.data.LongArrayListConverter;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Convert(converter = NewFamilyEtcConverter.class)
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private NewFamilyEtc etc;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private NewFamilyStatus status;

    @Column(name = "promote_date")
    private LocalDate promoteDate;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Convert(converter = LongArrayListConverter.class)
    @Column(name = "temporary_small_group_ids", columnDefinition = "json", nullable = false)
    private List<Long> temporarySmallGroupIds = new ArrayList<>();

    public static NewFamily from(LineOutNewFamily lineoutNewFamily) {
        return NewFamily.builder()
                .userId(lineoutNewFamily.getUserId())
                .visitDate(lineoutNewFamily.getVisitDate())
                .etc(lineoutNewFamily.getEtc())
                .status(NewFamilyStatus.NEW_FAMILY)
                .build();
    }

    @Builder
    public NewFamily(Long userId, Long newFamilyGroupId, LocalDate visitDate, NewFamilyEtc etc, NewFamilyStatus status) {
        this.userId = userId;
        this.newFamilyGroupId = newFamilyGroupId;
        this.visitDate = visitDate;
        this.etc = etc;
        this.status = status;
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

    public void updateVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public boolean statusEquals(NewFamilyStatus status) {
        return this.status == status;
    }

    public boolean statusNotEquals(NewFamilyStatus status) {
        return !this.status.equals(status);
    }

    public void toLineUpRequest() {
        if (status != NewFamilyStatus.NEW_FAMILY) {
            throw new IllegalStateException("현재 새가족인 상태에서만 라인업 요청 상태로 변경할 수 있습니다.");
        }

        status = NewFamilyStatus.LINE_UP_REQUESTED;
    }

    public void assignSmallGroup(Long smallGroupId) {
        if (status == NewFamilyStatus.NEW_FAMILY) {
            throw new IllegalStateException("라인업 요청을 먼저 진행해주세요.");
        }

        if (status == NewFamilyStatus.PROMOTED) {
            throw new IllegalStateException("이미 등반이 완료됐습니다.");
        }

        this.smallGroupId = smallGroupId;
        this.status = NewFamilyStatus.PROMOTE_CANDIDATE;
    }

    public void assignTemporarySmallGroups(List<Long> temporarySmallGroupIds) {
        if (status == NewFamilyStatus.NEW_FAMILY) {
            throw new IllegalStateException("라인업 요청을 먼저 진행해주세요.");
        }

        if (status == NewFamilyStatus.PROMOTED) {
            throw new IllegalStateException("이미 등반이 완료됐습니다.");
        }


        this.temporarySmallGroupIds = temporarySmallGroupIds;
    }

    public void promote(LocalDate date) {
        if (status != NewFamilyStatus.PROMOTE_CANDIDATE) {
            throw new IllegalStateException("등반 준비 상태가 아닙니다.");
        }

        if (smallGroupId == null) {
            throw new IllegalStateException("등반 이전에 라인업이 선행되어야합니다.");
        }

        status = NewFamilyStatus.PROMOTED;
        promoteDate = date;
    }
}
