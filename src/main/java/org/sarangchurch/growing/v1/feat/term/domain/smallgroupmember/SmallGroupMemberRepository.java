package org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember;

import java.util.List;
import java.util.Optional;

public interface SmallGroupMemberRepository {
    SmallGroupMember save(SmallGroupMember smallGroupMember);
    boolean existsByUserIdAndSmallGroupId(long userId, long smallGroupId);
    long countBySmallGroupId(Long smallGroupId);
    Optional<SmallGroupMember> findById(Long id);
    void deleteByTermIdAndUserIdIn(Long termId, List<Long> userIds);
    <S extends SmallGroupMember> List<S> saveAll(Iterable<S> entities);
    void deleteBySmallGroupId(Long smallGroupId);
}
