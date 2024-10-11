package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.*;
import org.sarangchurch.growing.v1.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;

@Repository
@RequiredArgsConstructor
public class NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyListItem> findAll() {
        List<Long> currentNewFamilyIds = queryFactory.select(newFamily.id)
                .from(newFamily)
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .where(newFamily.newFamilyPromoteLogId.isNull().or(newFamilyPromoteLog.promoteDate.isNull()))
                .fetch();

        // 새가족반 리더 지체
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        // 일반 순모임 리더 지체
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(NewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        newFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName")
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId), newFamily.id.in(currentNewFamilyIds))
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

    public List<NewFamilyListItem> findByNewFamilyGroup(Long newFamilyGroupId) {
        List<Long> currentNewFamilyIds = queryFactory.select(newFamily.id)
                .from(newFamily)
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .where(newFamily.newFamilyPromoteLogId.isNull().or(newFamilyPromoteLog.promoteDate.isNull()))
                .fetch();

        // 새가족반 리더 지체
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        // 일반 순모임 리더 지체
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(NewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        newFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName")
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId), newFamily.id.in(currentNewFamilyIds))
                // 새가족반
                .join(newFamilyGroup).on(
                        newFamilyGroup.id.eq(newFamily.newFamilyGroupId),
                        newFamilyGroup.id.eq(newFamilyGroupId)
                )
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 등반한 순모임
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .leftJoin(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public NewFamily findById(Long newFamilyId) {
        return queryFactory.select(Projections.constructor(NewFamily.class,
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

    public List<LineUpReadyNewFamilyListItem> findAllPromoteCandidates() {
        List<Long> promoteCandidateLogIds = queryFactory
                .select(newFamilyPromoteLog.id)
                .from(newFamilyPromoteLog)
                .where(newFamilyPromoteLog.promoteDate.isNull())
                .fetch();

        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LineUpReadyNewFamilyListItem.class,
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
                        newFamilyPromoteLog.temporarySmallGroupIds.as("temporarySmallGroupIds")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.newFamilyPromoteLogId.in(promoteCandidateLogIds)
                )
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 일반 순모임
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .leftJoin(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public List<PromotedNewFamilyListItem> findAllPromoted() {
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(PromotedNewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName"),
                        newFamilyPromoteLog.promoteDate.as("promoteDate")
                ))
                .from(newFamily)
                .join(newFamilyPromoteLog).on(
                        newFamily.newFamilyPromoteLogId.eq(newFamilyPromoteLog.id),
                        newFamilyPromoteLog.promoteDate.isNotNull()
                )
                .join(user).on(user.id.eq(newFamily.userId))
                // 일반 순모임
                .join(smallGroup).on(smallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public List<TemporaryLinedUpNewFamilyListItem> findAllTemporaryLinedUp() {
        return queryFactory.select(Projections.constructor(TemporaryLinedUpNewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        newFamilyPromoteLog.temporarySmallGroupIds.as("temporarySmallGroupIds")
                ))
                .from(newFamilyPromoteLog)
                .join(newFamily).on(
                        newFamily.newFamilyPromoteLogId.eq(newFamilyPromoteLog.id),
                        newFamilyPromoteLog.promoteDate.isNull()
                )
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }
}
