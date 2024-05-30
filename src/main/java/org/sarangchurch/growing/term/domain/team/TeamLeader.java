package org.sarangchurch.growing.term.domain.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamLeader extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "team_id")
    private Long teamId;
    private Long leaderId;
    @Enumerated(EnumType.STRING)
    private Duty duty;

    boolean matchLeaderId(Long leaderId) {
        return this.leaderId.equals(leaderId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamLeader that = (TeamLeader) o;
        return Objects.equals(teamId, that.teamId) && Objects.equals(leaderId, that.leaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, leaderId);
    }
}
