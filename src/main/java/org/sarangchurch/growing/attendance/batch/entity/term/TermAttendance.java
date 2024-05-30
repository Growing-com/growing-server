package org.sarangchurch.growing.attendance.batch.entity.term;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long termId;
    @Embedded
    private TermPersonalAttendances termPersonalAttendances = new TermPersonalAttendances();

    public static TermAttendance fromTerm(Long termId) {
        return new TermAttendance(termId);
    }

    private TermAttendance(Long termId) {
        this.termId = termId;
    }

    public void merge(List<? extends TermPersonalAttendance> termPersonalAttendances) {
        termPersonalAttendances.forEach(it -> it.setTermAttendanceId(id));
        this.termPersonalAttendances.merge(termPersonalAttendances);
    }
}
