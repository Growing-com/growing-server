package org.sarangchurch.growing.v1.feat.attendance.domain.visionreport;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vision_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Setter
    @Column(name = "active")
    private Integer active;

    @Setter
    @Column(name = "registered")
    private Integer registered;

    @Setter
    @Column(name = "total_attend")
    private Integer totalAttend;

    @Setter
    @Column(name = "new_family")
    private Integer newFamily;

    @Setter
    @Column(name = "new_family_attend")
    private Integer newFamilyAttend;

    public static VisionReport fromDate(LocalDate date) {
        return new VisionReport(date);
    }

    private VisionReport(LocalDate date) {
        this.date = date;
    }
}
