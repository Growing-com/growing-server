package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup;

public interface NewFamilyGroupMemberLineUpRepository {
    boolean existsByMemberUserIdAndTermId(Long userId, Long termId);
}
