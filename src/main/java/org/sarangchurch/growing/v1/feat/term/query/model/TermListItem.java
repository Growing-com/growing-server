package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TermListItem {
    private final Long termId;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final boolean isActive;
}
