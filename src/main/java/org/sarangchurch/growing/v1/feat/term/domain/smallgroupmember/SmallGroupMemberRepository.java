package org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember;

import java.util.Optional;

public interface SmallGroupMemberRepository {
    SmallGroupMember save(SmallGroupMember smallGroupMember);
    boolean existsByUserIdAndSmallGroupId(long userId, long smallGroupId);
    long countBySmallGroupId(Long smallGroupId);
    Optional<SmallGroupMember> findById(Long id);
}
