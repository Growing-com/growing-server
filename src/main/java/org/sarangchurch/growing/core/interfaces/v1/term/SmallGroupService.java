package org.sarangchurch.growing.core.interfaces.v1.term;

import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;

import java.util.List;

public interface SmallGroupService {
    void validateAvailable(List<Long> smallGroupIds);

    List<SmallGroup> findByCodyId(Long codyId);

    List<SmallGroup> findByTermId(Long termId);
}
