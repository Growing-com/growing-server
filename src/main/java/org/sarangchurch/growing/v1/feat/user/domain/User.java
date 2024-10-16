package org.sarangchurch.growing.v1.feat.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.core.interfaces.common.Sex;

import javax.persistence.*;
import java.time.LocalDate;


// TODO: V2에서는 User-Account 분리시킨다.
@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Builder
    public User(String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade, boolean isActive) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.sex = sex;
        this.grade = grade;
        this.isActive = isActive;
    }

    public UserEditor toEditor() {
        return UserEditor.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .sex(sex)
                .grade(grade)
                .build();
    }

    public void edit(UserEditor editor) {
        name = editor.getName();
        phoneNumber = editor.getPhoneNumber();
        birth = editor.getBirth();
        sex = editor.getSex();
        grade = editor.getGrade();
    }
}
