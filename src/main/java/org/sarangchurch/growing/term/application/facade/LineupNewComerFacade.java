package org.sarangchurch.growing.term.application.facade;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.application.dto.LineupNewComerRequest;
import org.sarangchurch.growing.term.application.service.TeamService;
import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistory;
import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistoryRepository;
import org.sarangchurch.growing.term.domain.team.Team;
import org.sarangchurch.growing.term.domain.team.TeamMember;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class LineupNewComerFacade {

    private final TeamService teamService;
    private final NewComerHistoryRepository newComerHistoryRepository;

    public void lineup(Long teamId, Long teamMemberId, LineupNewComerRequest request) {
        Team prevTeam = teamService.findById(teamId);
        Team nextTeam = teamService.findById(request.getPlantTeamId());

        TeamMember newComer = prevTeam.lineupNewComerTo(teamMemberId, nextTeam);
        Long userId = newComer.getMemberId();

        newComerHistoryRepository.save(NewComerHistory.builder()
                .userId(userId)
                .lineupWeek(Week.previousSunday(request.getLineupDate()))
                .gradeAtFirstVisit(request.getGradeAtFirstVisit())
                .newComerTeamId(teamId)
                .firstPlantTeamId(request.getPlantTeamId())
                .build());
    }

}
