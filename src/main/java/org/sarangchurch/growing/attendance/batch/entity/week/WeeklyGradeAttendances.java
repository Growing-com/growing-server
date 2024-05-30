package org.sarangchurch.growing.attendance.batch.entity.week;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class WeeklyGradeAttendances {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weeklyAttendanceId")
    private List<WeeklyGradeAttendance> gradeAttendances = new ArrayList<>();

    void merge(List<? extends WeeklyGradeAttendance> weeklyGradeAttendances) {
        weeklyGradeAttendances.forEach(it -> {
            Optional<WeeklyGradeAttendance> existingAttendance = findExisting(it);

            if (existingAttendance.isPresent()) {
                existingAttendance.get().merge(it);
            } else {
                gradeAttendances.add(it);
            }
        });
    }

    private Optional<WeeklyGradeAttendance> findExisting(WeeklyGradeAttendance attendance) {
        return gradeAttendances.stream()
                .filter(it -> it.getGrade().equals(attendance.getGrade()))
                .findAny();
    }
}
