package org.sarangchurch.growing.term.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewTeam {
    private final Long teamId;
    private final String leaderName;
}
