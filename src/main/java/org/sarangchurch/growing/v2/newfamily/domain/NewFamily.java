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
@TypeDef(name = "json", typeClass = JsonType.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "promote_date")
    private LocalDate promoteDate = null;

    @Type(type = "json")
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private Map<String, Object> etc = new HashMap<>();

    @Builder
    public NewFamily(String name, String phoneNumber, LocalDate birth, Integer grade, Long newFamilyGroupId, LocalDate visitDate, Map<String, Object> etc) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.newFamilyGroupId = newFamilyGroupId;
        this.visitDate = visitDate;
        this.etc = etc;
    }

    public void assignNewFamilyGroup(Long newFamilyGroupId) {
        this.newFamilyGroupId = newFamilyGroupId;
    }

    public void promote(LocalDate promoteDate) {
        if (this.promoteDate != null) {
            throw new IllegalStateException("이미 등반한 새가족입니다");
        }

        this.promoteDate = promoteDate;
    }
}
