package org.sarangchurch.growing.term.domain.team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);
    Optional<Team> findById(Long id);
    List<Team> findByIdIn(List<Long> ids);
    Team findOutTeam();
}
