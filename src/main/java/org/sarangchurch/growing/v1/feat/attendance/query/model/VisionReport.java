package org.sarangchurch.growing.v1.feat.attendance.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VisionReport {
    private final int active;
    private final int registered;
    private final int totalAttend;
    private final int newFamily;
    private final int newFamilyAttend;
}
