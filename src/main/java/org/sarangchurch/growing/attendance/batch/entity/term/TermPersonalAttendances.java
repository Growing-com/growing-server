package org.sarangchurch.growing.attendance.batch.entity.term;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
@Getter
public class TermPersonalAttendances {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "termAttendanceId")
    private List<TermPersonalAttendance> personalAttendances = new ArrayList<>();

    void merge(List<? extends TermPersonalAttendance> termPersonalAttendances) {
        termPersonalAttendances.forEach(it -> {
            Optional<TermPersonalAttendance> existingAttendance = findExisting(it);

            if (existingAttendance.isPresent()) {
                existingAttendance.get().merge(it);
            } else {
                personalAttendances.add(it);
            }
        });
    }

    private Optional<TermPersonalAttendance> findExisting(TermPersonalAttendance attendance) {
        return this.personalAttendances.stream()
                .filter(it -> it.getUserId().equals(attendance.getUserId()))
                .findAny();
    }
}
