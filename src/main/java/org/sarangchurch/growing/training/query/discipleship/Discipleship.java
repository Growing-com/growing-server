package org.sarangchurch.growing.training.query.discipleship;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Discipleship {
    private final Long id;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String etc;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DiscipleshipMember> members = new ArrayList<>();

    public void addMember(DiscipleshipMember member) {
        members.add(member);
    }

    public void setMembers(List<DiscipleshipMember> members) {
        this.members = members;
    }

    @Getter
    @RequiredArgsConstructor
    public static class DiscipleshipMember {
        private final Long userId;
        private final Long discipleshipId;
        private final Integer grade;
        private final String name;
        private final Sex sex;
        private final String phoneNumber;
    }
}
