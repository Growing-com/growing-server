package org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader;

public interface SmallGroupLeaderRepository {
    SmallGroupLeader save(SmallGroupLeader leader);

    boolean existsByUserIdAndTermId(Long userId, Long termId);

    void deleteById(Long smallGroupLeaderId);
}
