package org.sarangchurch.growing.core.interfaces.v1.term;

import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;

import java.util.List;

public interface CodyService {
    List<Cody> findByTermId(Long termId);

    boolean areValidMemberUserIdsByCodyId(Long codyId, List<Long> userIds);
}
