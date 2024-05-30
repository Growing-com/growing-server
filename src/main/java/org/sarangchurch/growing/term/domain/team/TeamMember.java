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
public class TeamMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, name = "team_id")
    private Long teamId;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private Duty duty;

    static TeamMember newComer(Long teamId, Long memberId) {
        return new TeamMember(teamId, memberId, Duty.NEW_COMER);
    }

    TeamMember(Long teamId, Long memberId, Duty duty) {
        this.memberId = memberId;
        this.teamId = teamId;
        this.duty = duty;
    }

    void lineupTo(Team team) {
        if (team.getType() != TeamType.PLANT) {
            throw new IllegalArgumentException("일반 순모임으로만 라인업 가능합니다.");
        }

        this.teamId = team.getId();
        this.duty = Duty.MEMBER;
    }

    void lineOut(Team team) {
        if (team.getType() != TeamType.OUT) {
            throw new IllegalArgumentException("라인아웃 모임으로만 라인아웃 가능합니다.");
        }

        this.teamId = team.getId();
        this.duty = Duty.OUT;
    }

    boolean matchById(Long id) {
        return this.id.equals(id);
    }

    boolean isNewComer() {
        return duty == Duty.NEW_COMER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMember that = (TeamMember) o;
        return Objects.equals(teamId, that.teamId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, memberId);
    }
}
