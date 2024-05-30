package org.sarangchurch.growing.training.domain.training;

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
public class TrainingMembers {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "training_id")
    private List<TrainingMember> trainingMembers = new ArrayList<>();

    void set(Long trainingId, List<Long> userIds) {
        trainingMembers.clear();
        userIds.forEach(it -> trainingMembers.add(TrainingMember.of(trainingId, it)));
    }

    List<Long> getUserIds() {
        return trainingMembers.stream()
                .map(TrainingMember::getUserId)
                .collect(Collectors.toList());
    }
}
