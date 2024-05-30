package org.sarangchurch.growing.term.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LineoutEvent {
    private final Long teamMemberId;
}
