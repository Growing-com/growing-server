package org.sarangchurch.growing.v2.term.infrastructure.jpa;

import org.sarangchurch.growing.v2.term.domain.SmallGroupMember;
import org.sarangchurch.growing.v2.term.domain.SmallGroupMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupMemberRepository extends JpaRepository<SmallGroupMember, Long>, SmallGroupMemberRepository {
}
