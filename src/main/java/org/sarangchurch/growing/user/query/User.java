package org.sarangchurch.growing.user.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class User {
    private final Long id;
    private final String name;
    private final Integer grade;
    private final Sex sex;
    private final Duty duty;
    private final Role role;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Boolean isActive;
    private final LocalDate visitDate;
    private final String etc;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    private Discipleship discipleship;
    private List<UserTraining> trainings = new ArrayList<>();

    public void setDiscipleship(Discipleship discipleship) {
        this.discipleship = discipleship;
    }

    public void setTrainings(List<UserTraining> trainings) {
        this.trainings = trainings;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Discipleship {
        private final String name;
        private final Long userId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class UserTraining {
        private final String name;
        private final Long userId;
        private final TrainingType type;
    }
}
