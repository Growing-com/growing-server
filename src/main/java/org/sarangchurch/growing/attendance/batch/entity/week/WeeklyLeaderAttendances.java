package org.sarangchurch.growing.attendance.batch.entity.week;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class WeeklyLeaderAttendances {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weeklyAttendanceId")
    private List<WeeklyLeaderAttendance> leaderAttendances = new ArrayList<>();

    void merge(List<? extends WeeklyLeaderAttendance> weeklyLeaderAttendances) {
        weeklyLeaderAttendances.forEach(it -> {
            Optional<WeeklyLeaderAttendance> existingAttendance = findExisting(it);

            if (existingAttendance.isPresent()) {
                existingAttendance.get().merge(it);
            } else {
                leaderAttendances.add(it);
            }
        });
    }

    private Optional<WeeklyLeaderAttendance> findExisting(WeeklyLeaderAttendance attendance) {
        return leaderAttendances.stream()
                .filter(it -> it.getLeaderId().equals(attendance.getLeaderId()))
                .findAny();
    }
}
