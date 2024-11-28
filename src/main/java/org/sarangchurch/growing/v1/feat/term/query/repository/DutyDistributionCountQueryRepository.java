package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.term.query.model.DutyDistributionCount;
import org.springframework.stereotype.Repository;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class DutyDistributionCountQueryRepository {
    private final JPAQueryFactory queryFactory;

    public DutyDistributionCount findByTerm(Long termId) {
        Long totalCount = queryFactory
                .select(user.count())
                .from(user)
                .where(user.isActive.isTrue())
                .fetchOne();

        Long pastorCount = queryFactory
                .select(pastor.count())
                .from(pastor)
                .where(pastor.termId.eq(termId))
                .fetchOne();

        Long codyCount = queryFactory
                .select(cody.count())
                .from(cody)
                .where(cody.termId.eq(termId))
                .fetchOne();

        Long smallGroupLeaderCount = queryFactory
                .select(smallGroup.count())
                .from(smallGroup)
                .where(smallGroup.termId.eq(termId))
                .fetchOne();

        Long newFamilyGroupLeaderCount = queryFactory
                .select(newFamilyGroup.count())
                .from(newFamilyGroup)
                .where(newFamilyGroup.termId.eq(termId))
                .fetchOne();

        Long smallGroupMemberCount = queryFactory
                .select(smallGroupMember.count())
                .from(smallGroupMember)
                .where(smallGroupMember.termId.eq(termId))
                .fetchOne();

        Long newFamilyGroupMemberCount = queryFactory
                .select(newFamilyGroupMember.count())
                .from(newFamilyGroupMember)
                .where(newFamilyGroupMember.termId.eq(termId))
                .fetchOne();

        Long newFamilyCount = queryFactory
                .select(newFamily.count())
                .from(newFamily)
                .where(newFamily.status.ne(NewFamilyStatus.PROMOTED))
                .fetchOne();

        Long notPlacedCount = totalCount - pastorCount - codyCount - smallGroupLeaderCount
                - newFamilyGroupLeaderCount - smallGroupMemberCount - newFamilyGroupMemberCount - newFamilyCount;

        return new DutyDistributionCount(
                totalCount,
                pastorCount,
                codyCount,
                smallGroupLeaderCount,
                newFamilyGroupLeaderCount,
                smallGroupMemberCount,
                newFamilyGroupMemberCount,
                newFamilyCount,
                notPlacedCount
        );
    }
}
