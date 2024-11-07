package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyMemberLineUpRepository extends JpaRepository<NewFamilyGroupMemberLineUp, Long>, NewFamilyGroupMemberLineUpRepository {
}
