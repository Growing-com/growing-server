package org.sarangchurch.growing.term.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.team.TeamType;

@Getter
@RequiredArgsConstructor
public class Leaders {
    private final Long teamId;
    private final TeamType teamType;
    private final String leaderName;
}
