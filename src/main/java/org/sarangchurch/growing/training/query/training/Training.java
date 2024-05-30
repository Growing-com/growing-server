package org.sarangchurch.growing.training.query.training;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Training {
    private final Long id;
    private final String name;
    private final TrainingType type;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String etc;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TrainingMember> members = new ArrayList<>();

    public void addMember(TrainingMember member) {
        members.add(member);
    }

    public void setMembers(List<TrainingMember> members) {
        this.members = members;
    }

    @Getter
    @RequiredArgsConstructor
    public static class TrainingMember {
        private final Long userId;
        private final Long trainingId;
        private final Integer grade;
        private final String name;
        private final Sex sex;
        private final String phoneNumber;
    }
}
