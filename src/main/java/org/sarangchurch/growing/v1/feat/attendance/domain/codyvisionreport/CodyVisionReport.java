package org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cody_vision_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodyVisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Column(name = "total")
    private Integer total;

    @Column(name = "attend")
    private Integer attend;

    @Builder
    public CodyVisionReport(LocalDate date, Long codyId, Integer total, Integer attend) {
        this.date = date;
        this.codyId = codyId;
        this.total = total;
        this.attend = attend;
    }
}
