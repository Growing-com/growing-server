package org.sarangchurch.growing.v1.feat.term.domain.term;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "term")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TermStatus status;

    @Builder
    public Term(String name, LocalDate startDate, LocalDate endDate, boolean isActive, TermStatus status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.status = status;
    }

    public Term toLineUpStatus() {
        isActive = false;
        status = TermStatus.LINE_UP;

        return this;
    }

    public boolean statusEquals(TermStatus anotherStatus) {
        return status == anotherStatus;
    }

    public void toClosedStatus() {
        isActive = false;
        status = TermStatus.CLOSED;
    }

    public void toActiveStatus() {
        isActive = true;
        status = TermStatus.ACTIVE;
    }
}
