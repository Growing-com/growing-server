package org.sarangchurch.growing.term.domain.team;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamLeaders {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private Set<TeamLeader> teamLeaders = new HashSet<>();

    boolean hasLeader(Long leaderId) {
        return this.teamLeaders.stream().anyMatch(it -> it.matchLeaderId(leaderId));
    }
}
