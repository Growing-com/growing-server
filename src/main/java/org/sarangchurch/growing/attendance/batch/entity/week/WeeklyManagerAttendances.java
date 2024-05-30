package org.sarangchurch.growing.attendance.batch.entity.week;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
@Getter
public class WeeklyManagerAttendances {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weeklyAttendanceId")
    private List<WeeklyManagerAttendance> managerAttendances = new ArrayList<>();

    void merge(List<? extends WeeklyManagerAttendance> weeklyManagerAttendances) {
        weeklyManagerAttendances.forEach(it -> {
            Optional<WeeklyManagerAttendance> existingAttendance = findExisting(it);

            if (existingAttendance.isPresent()) {
                existingAttendance.get().merge(it);
            } else {
                managerAttendances.add(it);
            }
        });
    }

    private Optional<WeeklyManagerAttendance> findExisting(WeeklyManagerAttendance attendance) {
        return managerAttendances.stream()
                .filter(it -> it.getManagerId().equals(attendance.getManagerId()))
                .findAny();
    }
}
