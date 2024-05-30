package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ForbiddenException;
import org.sarangchurch.growing.term.application.dto.AddNewComerToTeamRequest;
import org.sarangchurch.growing.term.domain.team.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow();
    }

    public Team findOutTeam() {
        return teamRepository.findOutTeam();
    }

    public TeamMember lineoutNewComer(Long teamId, Long teamMemberId) {
        Team newComerTeam = teamRepository.findById(teamId).orElseThrow();
        Team outTeam = findOutTeam();

        return newComerTeam.lineoutNewComerTo(teamMemberId, outTeam);
    }

    public void validateUserHasAccessToTeams(List<Long> teamIds, Long userId) {
        List<Team> teams = teamRepository.findByIdIn(teamIds);

        boolean hasAccessToAllTeams = teams.stream().allMatch(team -> team.hasAccess(userId));

        if (!hasAccessToAllTeams) {
            throw new ForbiddenException("순모임 권한이 없습니다.");
        }
    }

    public void validateTeamMembersBelongToAnyTeam(List<Long> teamIds, List<Long> teamMemberIds) {
        List<Team> teams = teamRepository.findByIdIn(teamIds);

        boolean isEveryMemberValid = teamMemberIds.stream().allMatch(belongsToAnyTeam(teams));

        if (!isEveryMemberValid) {
            throw new IllegalArgumentException("순모임에 소속되지 않은 순원이 있습니다.");
        }
    }

    private Predicate<Long> belongsToAnyTeam(List<Team> teams) {
        return teamMemberId -> teams.stream().anyMatch(team -> team.hasTeamMember(teamMemberId));
    }

    public void addNewComer(AddNewComerToTeamRequest request) {
        teamRepository.findById(request.getTeamId()).orElseThrow()
                .addNewComer(request.getMemberId());
    }
}
