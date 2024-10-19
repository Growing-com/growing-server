package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupMemberRepository extends JpaRepository<NewFamilyGroupMember, Long>, NewFamilyGroupMemberRepository {
}
