package org.sarangchurch.growing.v1.feat.newfamily.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewFamiliesEmitEvent {
    private final List<Long> newFamilyIds;
}
