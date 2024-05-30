package org.sarangchurch.growing.core.types;

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
public class Week implements Comparable<Week> {
    private LocalDate week;

    public static Week previousSunday(LocalDate date) {
        return new Week(date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
    }

    private Week(LocalDate week) {
        this.week = week;
    }

    public boolean isBefore(Week anotherWeek) {
        return week.isBefore(anotherWeek.week);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week1 = (Week) o;
        return Objects.equals(week, week1.week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(week);
    }

    @Override
    public String toString() {
        return week.toString();
    }

    @Override
    public int compareTo(Week o) {
        if (week.equals(o.getWeek())) return 0;
        if (week.isBefore(o.getWeek())) return -1;
        return 1;
    }
}
