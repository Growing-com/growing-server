package org.sarangchurch.growing.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "v1_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private String phoneNumber;
    private LocalDate birth;
    private Integer grade;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isActive;
    private Long visitTermId;
    private LocalDate visitDate;
    @Column(columnDefinition = "TEXT")
    private String etc;

    @Builder
    public UserEntity(
            String username,
            String password,
            String name,
            Sex sex,
            String phoneNumber,
            LocalDate birth,
            Integer grade,
            Role role,
            Boolean isActive,
            Long visitTermId,
            LocalDate visitDate,
            String etc
    ) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.role = role;
        this.isActive = isActive;
        this.visitTermId = visitTermId;
        this.visitDate = visitDate;
        this.etc = etc;
    }

    public void update(
        String name,
        Sex sex,
        String phoneNumber,
        LocalDate birth,
        Integer grade,
        Boolean isActive,
        LocalDate visitDate,
        String etc
    ) {
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.grade = grade;
        this.isActive = isActive;
        this.visitDate = visitDate;
        this.etc = etc;
    }

    public boolean matchName(String anotherName) {
        return name.equals(anotherName);
    }
}
