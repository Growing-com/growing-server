package org.sarangchurch.growing.v1.feat.attendance.batch;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sunday implements Comparable<Sunday> {
    private LocalDate date;

    public static Sunday previousSunday(LocalDate date) {
        return new Sunday(date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
    }

    private Sunday(LocalDate date) {
        this.date = date;
    }

    public Sunday previous() {
        return Sunday.previousSunday(date.minusWeeks(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sunday sunday1 = (Sunday) o;
        return Objects.equals(date, sunday1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public int compareTo(Sunday o) {
        if (date.equals(o.getDate())) return 0;
        if (date.isBefore(o.getDate())) return -1;
        return 1;
    }
}
