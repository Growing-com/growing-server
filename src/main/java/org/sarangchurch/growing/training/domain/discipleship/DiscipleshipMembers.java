package org.sarangchurch.growing.training.domain.discipleship;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscipleshipMembers {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipleship_id")
    private List<DiscipleshipMember> discipleshipMembers = new ArrayList<>();

    void set(Long trainingId, List<Long> userIds) {
        discipleshipMembers.clear();
        userIds.forEach(it -> discipleshipMembers.add(DiscipleshipMember.of(trainingId, it)));
    }

    List<Long> getUserIds() {
        return discipleshipMembers.stream()
                .map(DiscipleshipMember::getUserId)
                .collect(Collectors.toList());
    }
}
