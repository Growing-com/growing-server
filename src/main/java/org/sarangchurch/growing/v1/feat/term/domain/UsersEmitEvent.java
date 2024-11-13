package org.sarangchurch.growing.v1.feat.term.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class UsersEmitEvent {
    private final List<Long> userIds;
}
