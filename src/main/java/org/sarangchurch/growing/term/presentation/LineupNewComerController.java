package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.term.application.facade.LineupNewComerFacade;
import org.sarangchurch.growing.term.application.dto.LineupNewComerRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LineupNewComerController {

    private final LineupNewComerFacade lineupNewComerFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/team/{teamId}/teamMember/{teamMemberId}/lineup")
    public void lineup(
            @PathVariable Long teamId,
            @PathVariable Long teamMemberId,
            @RequestBody @Valid LineupNewComerRequest request
    ) {
        lineupNewComerFacade.lineup(teamId, teamMemberId, request);
    }
}
