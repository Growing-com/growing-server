package org.sarangchurch.growing.v2.feat.newfamily.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamily> findAll() {
        // 새가족반
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        // 새가족일때 일반 순모임
        QSmallGroup smallGroup = new QSmallGroup("smallGroup");
        QSmallGroupLeader smallGroupLeader = new QSmallGroupLeader("smallGroupLeader");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        // 등반한 순모임
        QSmallGroup promotedSmallGroup = new QSmallGroup("promotedSmallGroup");
        QSmallGroupLeader promotedSmallGroupLeader = new QSmallGroupLeader("promotedSmallGroupLeader");
        QUser promotedSmallGroupLeaderUser = new QUser("promotedSmallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(NewFamily.class,
                        newFamily.id,
                        user.name,
                        user.phoneNumber,
                        user.birth,
                        user.gender,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name,
                        promotedSmallGroupLeaderUser.name
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId))
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 새가족일때 일반 순모임
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .leftJoin(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                // 등반한 순모임
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .leftJoin(promotedSmallGroup).on(promotedSmallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .leftJoin(promotedSmallGroupLeader).on(promotedSmallGroupLeader.id.eq(promotedSmallGroup.smallGroupLeaderId))
                .leftJoin(promotedSmallGroupLeaderUser).on(promotedSmallGroupLeaderUser.id.eq(promotedSmallGroupLeader.userId))
                .fetch();
    }
}
