package org.sarangchurch.growing.core.interfaces.v1.term;

import java.util.List;

public interface SmallGroupMemberService {
    void create(Long userId, Long smallGroupId);

    void update(Long userId, Long targetSmallGroupId);

    long countBySmallGroupIdIn(List<Long> smallGroupIds);
}
