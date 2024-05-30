package org.sarangchurch.growing.term.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TotalUser {
    private final Long totalRegistered;
    private final Long totalNewRegistered;
}
