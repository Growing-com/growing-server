package org.sarangchurch.growing.training.domain.training;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrainingMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "training_id")
    private Long trainingId;
    private Long userId;

    static TrainingMember of(Long trainingId, Long userId) {
        return new TrainingMember(trainingId, userId);
    }

    private TrainingMember(Long trainingId, Long userId) {
        this.trainingId = trainingId;
        this.userId = userId;
    }
}
