package org.sarangchurch.growing.v1.core.interfaces.newfamily;

import java.util.List;

public interface NewFamilyService {
    boolean existsByIds(List<Long> ids);
}
