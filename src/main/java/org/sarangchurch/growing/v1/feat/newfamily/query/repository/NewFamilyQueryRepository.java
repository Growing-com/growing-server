package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.LineUpReadyNewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.PromotedNewFamilyListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.attendance.QAttendance.attendance;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyListItem> findAll() {
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
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.status.ne(NewFamilyStatus.PROMOTED)
                )
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 등반한 순모임
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroup.leaderUserId))
                // 정렬
                .orderBy(newFamily.visitDate.desc())
                .fetch();
    }

    public List<NewFamilyListItem> findByNewFamilyGroup(Long newFamilyGroupId) {
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
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.status.ne(NewFamilyStatus.PROMOTED)
                )
                // 새가족반
                .join(newFamilyGroup).on(
                        newFamilyGroup.id.eq(newFamily.newFamilyGroupId),
                        newFamilyGroup.id.eq(newFamilyGroupId)
                )
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 등반한 순모임
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroup.leaderUserId))
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
                        newFamily.temporarySmallGroupIds.as("temporarySmallGroupIds")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.status.in(NewFamilyStatus.PROMOTE_CANDIDATE, NewFamilyStatus.LINE_UP_REQUESTED)
                )
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 일반 순모임
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroup.leaderUserId))
                // 정렬
                .orderBy(newFamily.visitDate.asc())
                .fetch();
    }

    public List<PromotedNewFamilyListItem> findAllPromoted() {
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        LocalDate oneYearBefore = LocalDate.now().minusYears(1);

        List<PromotedNewFamilyListItem> promotedNewFamilies = queryFactory.select(Projections.constructor(PromotedNewFamilyListItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.phoneNumber.as("phoneNumber"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName"),
                        newFamily.promoteDate.as("promoteDate"),
                        user.id.as("userId")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.status.eq(NewFamilyStatus.PROMOTED),
                        newFamily.promoteDate.after(oneYearBefore)
                )
                // 일반 순모임
                .join(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroup.leaderUserId))
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 정렬
                .orderBy(newFamily.promoteDate.desc())
                .fetch();

        List<Long> promotedNewFamilyUserIds = promotedNewFamilies.stream().map(PromotedNewFamilyListItem::getUserId).collect(Collectors.toList());

        List<Attendance> attendances = queryFactory.select(attendance)
                .from(attendance)
                .where(attendance.userId.in(promotedNewFamilyUserIds))
                .fetch();

        promotedNewFamilies.forEach(promotedNewFamily -> {
            long attendanceAfterPromotion = attendances.stream()
                    .filter(attendance -> {
                        if (!attendance.getUserId().equals(promotedNewFamily.getUserId())) {
                            return false;
                        }

                        return attendance.getDate().isAfter(promotedNewFamily.getPromoteDate());
                    })
                    .count();

            promotedNewFamily.setAttendanceAfterPromotion(attendanceAfterPromotion);
        });

        return promotedNewFamilies;
    }
}
