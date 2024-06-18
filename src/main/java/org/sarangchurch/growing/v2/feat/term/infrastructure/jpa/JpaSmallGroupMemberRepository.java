package org.sarangchurch.growing.v2.feat.term.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupMemberRepository extends JpaRepository<SmallGroupMember, Long>, SmallGroupMemberRepository {
}
