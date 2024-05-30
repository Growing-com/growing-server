package org.sarangchurch.growing.training.domain.discipleship;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscipleshipMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "discipleship_id")
    private Long discipleShipId;
    private Long userId;

    static DiscipleshipMember of(Long discipleShipId, Long userId) {
        return new DiscipleshipMember(discipleShipId, userId);
    }

    private DiscipleshipMember(Long discipleShipId, Long userId) {
        this.discipleShipId = discipleShipId;
        this.userId = userId;
    }
}
