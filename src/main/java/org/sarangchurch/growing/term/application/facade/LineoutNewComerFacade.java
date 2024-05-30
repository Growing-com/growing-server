package org.sarangchurch.growing.term.application.facade;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.application.dto.LineoutNewComerRequest;
import org.sarangchurch.growing.term.application.service.LineoutUserService;
import org.sarangchurch.growing.term.application.service.TeamService;
import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistory;
import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistoryRepository;
import org.sarangchurch.growing.term.domain.team.TeamMember;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class LineoutNewComerFacade {

    private final TeamService teamService;
    private final LineoutUserService lineoutUserService;
    private final NewComerHistoryRepository newComerHistoryRepository;

    public void lineout(Long teamId, Long teamMemberId, LineoutNewComerRequest request) {
        TeamMember outMember = teamService.lineoutNewComer(teamId, teamMemberId);
        Long userId = outMember.getMemberId();

        lineoutUserService.lineout(userId);
        newComerHistoryRepository.save(NewComerHistory.builder()
                .userId(userId)
                .lineoutWeek(Week.previousSunday(request.getLineoutDate()))
                .gradeAtFirstVisit(request.getGradeAtFirstVisit())
                .newComerTeamId(teamId)
                .build());
    }
}
