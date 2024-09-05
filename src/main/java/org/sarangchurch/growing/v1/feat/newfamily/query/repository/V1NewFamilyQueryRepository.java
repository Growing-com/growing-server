package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1LineUpReadyNewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamilyListItem;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class V1NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<V1NewFamilyListItem> findAll() {
        // 새가족반 리더 지체
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        // 일반 순모임 리더 지체
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(V1NewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        newFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        newFamily.etc.as("etc"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName"),
                        newFamilyPromoteLog.promoteDate.as("promoteDate")
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId))
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 등반한 순모임
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .leftJoin(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public V1NewFamily findById(Long newFamilyId) {
        return queryFactory.select(Projections.constructor(V1NewFamily.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        newFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        newFamily.etc.as("etc"),
                        newFamilyGroup.id.as("newFamilyGroupId")
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId), newFamily.id.eq(newFamilyId))
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .fetchOne();
    }

    public List<V1LineUpReadyNewFamilyListItem> findAllLineUpReady() {
        List<Long> lineUpReadyPromoteLogIds = queryFactory
                .select(newFamilyPromoteLog.id)
                .from(newFamilyPromoteLog)
                .where(
                        newFamilyPromoteLog.smallGroupId.isNull(),
                        newFamilyPromoteLog.promoteDate.isNull()
                )
                .fetch();


        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        return queryFactory.select(Projections.constructor(V1LineUpReadyNewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        newFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        newFamily.etc.as("etc"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.newFamilyPromoteLogId.in(lineUpReadyPromoteLogIds)
                )
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }
}
