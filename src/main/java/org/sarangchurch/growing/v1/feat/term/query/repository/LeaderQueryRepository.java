package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.LeaderListItem;
import org.sarangchurch.growing.v1.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class LeaderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<LeaderListItem> findCodiesByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("codyName")
                ))
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.termId.eq(termId))
                .fetch();
    }

    public List<LeaderListItem> findNewFamilyLeadersByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        newFamilyGroupLeaderUser.id.as("userId"),
                        newFamilyGroupLeaderUser.name.as("name"),
                        newFamilyGroupLeaderUser.sex.as("sex"),
                        newFamilyGroupLeaderUser.grade.as("grade"),
                        newFamilyGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        newFamilyGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroup.termId.eq(termId))
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }

    public List<LeaderListItem> findSmallGroupLeadersByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        smallGroupLeaderUser.id.as("userId"),
                        smallGroupLeaderUser.name.as("name"),
                        smallGroupLeaderUser.sex.as("sex"),
                        smallGroupLeaderUser.grade.as("grade"),
                        smallGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        smallGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId), smallGroup.termId.eq(termId))
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }
}
