package org.sarangchurch.growing.term.domain.newComerHistory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewComerHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Embedded
    @AttributeOverride(name = "week", column = @Column(name = "line_up_week"))
    private Week lineupWeek;
    @Embedded
    @AttributeOverride(name = "week", column = @Column(name = "line_out_week"))
    private Week lineoutWeek;
    private Integer gradeAtFirstVisit;
    private Long newComerTeamId;
    private Long firstPlantTeamId;

    @Builder
    public NewComerHistory(
            Long userId,
            Week lineupWeek,
            Week lineoutWeek,
            Integer gradeAtFirstVisit,
            Long newComerTeamId,
            Long firstPlantTeamId
    ) {
        this.userId = userId;
        this.lineupWeek = lineupWeek;
        this.lineoutWeek = lineoutWeek;
        this.gradeAtFirstVisit = gradeAtFirstVisit;
        this.newComerTeamId = newComerTeamId;
        this.firstPlantTeamId = firstPlantTeamId;
    }
}
