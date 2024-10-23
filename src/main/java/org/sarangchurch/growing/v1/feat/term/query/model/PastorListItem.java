package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PastorListItem {
    private final Long pastorId;
    private final String pastorName;
    private final Boolean isSenior;
}
