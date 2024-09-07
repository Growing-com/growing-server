package org.sarangchurch.growing.v1.core.interfaces.newfamily;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;

public interface NewFamilyGroupService {
    NewFamilyGroup findById(Long id);
}
