package org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember;

import java.util.List;
import java.util.Optional;

public interface SmallGroupMemberRepository {
    boolean existsByUserIdAndSmallGroupId(long userId, long smallGroupId);

    long countBySmallGroupId(Long smallGroupId);

    boolean existsByUserIdAndTermId(Long userId, Long termId);

    Optional<SmallGroupMember> findById(Long id);

    SmallGroupMember save(SmallGroupMember smallGroupMember);

    <S extends SmallGroupMember> List<S> saveAll(Iterable<S> entities);

    void deleteBySmallGroupId(Long smallGroupId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    Optional<SmallGroupMember> findByUserIdAndTermId(Long userId, Long termId);
}
