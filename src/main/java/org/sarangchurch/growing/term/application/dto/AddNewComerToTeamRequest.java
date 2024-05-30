package org.sarangchurch.growing.term.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddNewComerToTeamRequest {
    private final Long teamId;
    private final Long memberId;
}
