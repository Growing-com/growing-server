package org.sarangchurch.growing.term.domain.team;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long termId;
    private Long leaderId;
    private Long managerId;
    @Enumerated(EnumType.STRING)
    private TeamType type;
    @Embedded
    private TeamLeaders teamLeaders = new TeamLeaders();
    @Embedded
    private TeamMembers teamMembers = new TeamMembers();

    @Builder
    Team(Long id, String name, Long termId, Long leaderId, Long managerId, TeamType type) {
        this.id = id;
        this.name = name;
        this.termId = termId;
        this.leaderId = leaderId;
        this.managerId = managerId;
        this.type = type;
    }

    public void addNewComer(Long memberId) {
        if (type != TeamType.NEW) {
            throw new IllegalArgumentException("새가족반에만 등록할 수 있습니다.");
        }

        teamMembers.addNewComer(id, memberId);
    }

    public boolean hasAccess(Long userId) {
        return leaderId.equals(userId) || managerId.equals(userId) || teamLeaders.hasLeader(userId);
    }

    public boolean hasTeamMember(Long teamMemberId) {
        return teamMembers.hasTeamMember(teamMemberId);
    }

    public TeamMember lineupNewComerTo(Long teamMemberId, Team plantTeam) {
        validateExistingNewComer(teamMemberId);
        TeamMember newComer = teamMembers.findTeamMemberById(teamMemberId);
        newComer.lineupTo(plantTeam);
        return newComer;
    }

    public TeamMember lineoutNewComerTo(Long teamMemberId, Team outTeam) {
        validateExistingNewComer(teamMemberId);
        TeamMember newComer = teamMembers.findTeamMemberById(teamMemberId);
        newComer.lineOut(outTeam);
        return newComer;
    }

    private void validateExistingNewComer(Long teamMemberId) {
        if (type != TeamType.NEW) {
            throw new IllegalArgumentException("이 순모임은 새가족 순모임이 아닙니다.");
        }

        if (!hasTeamMember(teamMemberId)) {
            throw new IllegalArgumentException("새가족 순모임에 소속되지 않은 조원입니다.");
        }

        if (!teamMembers.isNewComer(teamMemberId)) {
            throw new IllegalArgumentException("이 조원은 새가족이 아닙니다.");
        }
    }
}
