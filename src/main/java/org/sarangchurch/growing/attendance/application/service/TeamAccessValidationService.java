package org.sarangchurch.growing.attendance.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.application.dto.RegisterAttendanceRequest;
import org.sarangchurch.growing.attendance.domain.user.User;
import org.sarangchurch.growing.attendance.infra.UserDownStream;
import org.sarangchurch.growing.term.application.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamAccessValidationService {
    private final UserDownStream userDownStream;
    private final TeamService teamService;

    public void validateHasAccess(Long userId, RegisterAttendanceRequest request) {
        User user = userDownStream.findById(userId);

        if (user.isAdmin()) {
            return;
        }

        teamService.validateUserHasAccessToTeams(request.teamIds(), userId);
        teamService.validateTeamMembersBelongToAnyTeam(request.teamIds(), request.teamMemberIds());
    }
}
