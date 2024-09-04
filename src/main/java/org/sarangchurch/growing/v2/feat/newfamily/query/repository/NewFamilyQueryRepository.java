package org.sarangchurch.growing.v2.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.PromotedNewFamily;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public NewFamily findById(Long newFamilyId) {
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
                        user.sex,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name,
                        promotedSmallGroupLeaderUser.name,
                        newFamilyPromoteLog.promoteDate
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId), newFamily.id.eq(newFamilyId))
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
                .fetchOne();
    }

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
                        user.sex,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name,
                        promotedSmallGroupLeaderUser.name,
                        newFamilyPromoteLog.promoteDate
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
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public List<NewFamily> findByNewFamilyGroup(Long newFamilyGroupId) {
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
                        user.sex,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name,
                        promotedSmallGroupLeaderUser.name,
                        newFamilyPromoteLog.promoteDate
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId))
                // 새가족반
                .innerJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId),
                        newFamilyGroup.id.eq(newFamilyGroupId))
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
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public List<PromotedNewFamily> findAllPromotedNewFamily() {
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

        return queryFactory.select(Projections.constructor(PromotedNewFamily.class,
                        newFamily.id,
                        user.name,
                        user.phoneNumber,
                        user.birth,
                        user.sex,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name,
                        promotedSmallGroupLeaderUser.name,
                        newFamilyPromoteLog.promoteDate
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
                .innerJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .innerJoin(promotedSmallGroup).on(promotedSmallGroup.id.eq(newFamilyPromoteLog.smallGroupId))
                .innerJoin(promotedSmallGroupLeader).on(promotedSmallGroupLeader.id.eq(promotedSmallGroup.smallGroupLeaderId))
                .innerJoin(promotedSmallGroupLeaderUser).on(promotedSmallGroupLeaderUser.id.eq(promotedSmallGroupLeader.userId))
                // 정렬
                .orderBy(newFamilyPromoteLog.promoteDate.desc())
                .fetch();
    }
}
