package org.sarangchurch.growing.v2.newfamily.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "new_family")
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
            Integer grade,
            Long newFamilyGroupId,
            LocalDate visitDate,
            Map<String, Object> etc,
            Long userId
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.newFamilyGroupId = newFamilyGroupId;
        this.visitDate = visitDate;
        this.etc = etc;
        this.userId = userId;
    }

    public void assignNewFamilyGroup(Long newFamilyGroupId) {
        this.newFamilyGroupId = newFamilyGroupId;
    }

    public void promote(LocalDate promoteDate) {
//        newFamilyPromoteLogId
    }
}
