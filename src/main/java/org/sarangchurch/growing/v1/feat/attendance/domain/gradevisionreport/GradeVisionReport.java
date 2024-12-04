package org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grade_vision_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradeVisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "total")
    private Integer total;

    @Column(name = "attend")
    private Integer attend;

    @Builder
    public GradeVisionReport(LocalDate date, Integer grade, Integer total, Integer attend) {
        this.date = date;
        this.grade = grade;
        this.total = total;
        this.attend = attend;
    }
}
