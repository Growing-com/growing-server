package org.sarangchurch.growing.term.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.term.TermStatus;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Term {
    private final Long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final TermStatus status;
    private final String memo;
}
