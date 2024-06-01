package org.sarangchurch.growing.v2.newfamily.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "new_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "new_family_leader_id")
    private Long newFamilyLeaderId;

    @Type(type = "json")
    @Column(name = "etc", columnDefinition = "json")
    private Map<String, Object> etc = new HashMap<>();

    @Builder
    public NewFamily(String name, String phoneNumber, LocalDate birth, Integer grade, Long newFamilyLeaderId, Map<String, Object> etc) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.newFamilyLeaderId = newFamilyLeaderId;
        this.etc = etc;
    }
}
