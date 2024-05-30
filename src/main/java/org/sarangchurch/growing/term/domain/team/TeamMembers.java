package org.sarangchurch.growing.term.domain.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamMembers {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private Set<TeamMember> teamMembers = new HashSet<>();

    void addNewComer(Long teamId, Long memberId) {
        TeamMember teamMember = TeamMember.newComer(teamId, memberId);

        if (teamMembers.contains(teamMember)) {
            throw new IllegalStateException("Duplicate member: " + memberId);
        }

        teamMembers.add(teamMember);
    }

    boolean hasTeamMember(Long teamMemberId) {
        return teamMembers.stream().anyMatch(it -> it.matchById(teamMemberId));
    }

    boolean isNewComer(Long teamMemberId) {
        TeamMember teamMember = teamMembers.stream()
                .filter(it -> it.matchById(teamMemberId))
                .findAny()
                .orElseThrow();

        return teamMember.isNewComer();
    }

    TeamMember findTeamMemberById(Long id) {
        return teamMembers.stream()
                .filter(it -> it.matchById(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순원입니다."));
    }
}
