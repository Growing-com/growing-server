package org.sarangchurch.growing.v2.newfamily.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.types.BaseEntity;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "new_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class NewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 인적 정보 START //
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Type(type = "json")
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private Map<String, Object> etc = new HashMap<>();
    // 인적 정보 END //

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Column(name = "new_family_promote_log_id")
    private Long newFamilyPromoteLogId;

    @Column(name = "small_group_id")
    private Long smallGroupId; // 새가족반이 아닌 일반 순모임에 배정된 특이 케이스

    @Builder
    public NewFamily(
            String name,
            String phoneNumber,
            LocalDate birth,
            Gender gender,
            Integer grade,
            Long newFamilyGroupId,
            LocalDate visitDate,
            Map<String, Object> etc,
            Long userId
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.grade = grade;
        this.newFamilyGroupId = newFamilyGroupId;
        this.visitDate = visitDate;
        this.etc = etc;
        this.userId = userId;
    }

    public void assignSmallGroup(Long smallGroupId) {
        if (this.newFamilyPromoteLogId != null) {
            throw new IllegalStateException("이미 등반했습니다.");
        }

        if (this.newFamilyGroupId != null) {
            throw new IllegalStateException("이미 새가족반에 배정되었습니다.");
        }

        if (this.smallGroupId != null) {
            throw new IllegalStateException("이미 순모임에 배정되었습니다.");
        }

        this.smallGroupId = smallGroupId;
    }

    public void assignNewFamilyGroup(Long newFamilyGroupId) {
        if (this.newFamilyPromoteLogId != null) {
            throw new IllegalStateException("이미 등반했습니다.");
        }

        if (this.newFamilyGroupId != null) {
            throw new IllegalStateException("이미 새가족반에 배정되었습니다.");
        }

        this.newFamilyGroupId = newFamilyGroupId;
    }

    public void promote(Long newFamilyPromoteLogId) {
        if (this.newFamilyPromoteLogId != null) {
            throw new IllegalStateException("이미 등반했습니다.");
        }

        this.newFamilyPromoteLogId = newFamilyPromoteLogId;
    }
}
