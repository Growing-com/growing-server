package org.sarangchurch.growing.v1.feat.term.domain.smallgroup;

import java.util.List;

public interface SmallGroupRepository {
    List<SmallGroup> findByIdIn(List<Long> ids);
}
