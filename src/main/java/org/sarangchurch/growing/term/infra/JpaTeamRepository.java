package org.sarangchurch.growing.term.infra;

import org.sarangchurch.growing.term.domain.team.Team;
import org.sarangchurch.growing.term.domain.team.TeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaTeamRepository extends JpaRepository<Team, Long>, TeamRepository {

    @Override
    @Query("select t from Team t where t.type = 'OUT'")
    Team findOutTeam();
}
