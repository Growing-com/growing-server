package org.sarangchurch.growing.core.interfaces.v1.term;

import java.util.List;

public interface SmallGroupService {
    void validateAvailable(List<Long> smallGroupIds);

    boolean areValidUserIdsBySmallGroupId(List<Long> userIds, Long smallGroupId);
}
