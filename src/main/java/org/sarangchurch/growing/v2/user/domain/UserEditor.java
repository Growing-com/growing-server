package org.sarangchurch.growing.v2.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

import java.time.LocalDate;

@Getter
public class UserEditor {
    private String name;
    private String phoneNumber;
    private LocalDate birth;
    private Gender gender;
    private Integer grade;

    @Builder
    public UserEditor(String name, String phoneNumber, LocalDate birth, Gender gender, Integer grade) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
