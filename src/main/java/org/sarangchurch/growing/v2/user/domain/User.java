package org.sarangchurch.growing.v2.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

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
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Builder
    public User(String name, String phoneNumber, LocalDate birth, Gender gender, Integer grade) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.grade = grade;
    }

    public UserEditor toEditor() {
        return UserEditor.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .gender(gender)
                .grade(grade)
                .build();
    }

    public void edit(UserEditor editor) {
        name = editor.getName();
        phoneNumber = editor.getPhoneNumber();
        birth = editor.getBirth();
        gender = editor.getGender();
        grade = editor.getGrade();
    }
}
