package org.sarangchurch.growing.v2.feat.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.v2.core.interfaces.common.Sex;

import java.time.LocalDate;

@Getter
public class UserEditor {
    private String name;
    private String phoneNumber;
    private LocalDate birth;
    private Sex sex;
    private Integer grade;

    @Builder
    public UserEditor(String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.sex = sex;
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

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
