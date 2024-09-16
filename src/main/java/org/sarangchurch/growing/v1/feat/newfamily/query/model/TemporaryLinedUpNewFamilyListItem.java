package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TemporaryLinedUpNewFamilyListItem {
    private final Long newFamilyId;
    private final List<Long> temporarySmallGroupIds;
}
