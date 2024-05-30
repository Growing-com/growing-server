package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.term.application.facade.LineoutNewComerFacade;
import org.sarangchurch.growing.term.application.dto.LineoutNewComerRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LineoutNewComerController {

    private final LineoutNewComerFacade lineoutNewComerFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/team/{teamId}/teamMember/{teamMemberId}/lineout")
    public void lineout(
            @PathVariable Long teamId,
            @PathVariable Long teamMemberId,
            @RequestBody @Valid LineoutNewComerRequest request
    ) {
        lineoutNewComerFacade.lineout(teamId, teamMemberId, request);
    }
}
