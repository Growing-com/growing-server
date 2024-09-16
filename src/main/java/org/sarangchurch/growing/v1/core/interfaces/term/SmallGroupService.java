package org.sarangchurch.growing.v1.core.interfaces.term;

import java.util.List;

public interface SmallGroupService {
    void validateAvailable(List<Long> smallGroupIds);
}
