package org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader;

public interface SmallGroupLeaderRepository {
    SmallGroupLeader save(SmallGroupLeader leader);

    boolean existsByTermIdAndUserId(Long termId, Long userId);

    void deleteById(Long smallGroupLeaderId);
}
