package org.sarangchurch.growing.v2.feat.attendance.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v2.feat.attendance.domain.newfamilyattendance.QNewFamilyAttendance.newFamilyAttendance;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyAttendance> findPromotedIncludedByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime cursor,
            int limit
    ) {
        List<NewFamilyAttendance> newFamilyAttendances =  this.findNewFamiliesByCursorAndLimit(cursor, limit);

        List<Long> newFamilyIds = newFamilyAttendances.stream()
                .map(NewFamilyAttendance::getNewFamilyId)
                .collect(Collectors.toList());

        Map<Long, List<NewFamilyAttendanceItem>> idToAttendanceItems = this.findAttendancesByIdInAndDateRange(
                newFamilyIds,
                startDate,
                endDate
        );

        newFamilyAttendances.forEach(it -> {
            List<NewFamilyAttendanceItem> attendanceItems = idToAttendanceItems.getOrDefault(it.getNewFamilyId(), Collections.emptyList());

            attendanceItems.sort(Comparator.comparing(NewFamilyAttendanceItem::getDate).reversed());

            it.setAttendanceItems(attendanceItems);
        });

        return newFamilyAttendances;
    }

    private List<NewFamilyAttendance> findNewFamiliesByCursorAndLimit(LocalDateTime cursor, int limit) {
        LocalDateTime baseCreatedAt = queryFactory.select(newFamily.createdAt)
                .from(newFamily)
                .where(newFamily.createdAt.loe(cursor))
                .orderBy(newFamily.createdAt.desc())
                .fetchFirst();

        if (baseCreatedAt == null) {
            return Collections.emptyList();
        }

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

        return queryFactory.select(Projections.constructor(NewFamilyAttendance.class,
                        newFamily.id,
                        newFamily.createdAt,
                        user.name,
                        user.phoneNumber,
                        user.birth,
                        user.gender,
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
                // filter, sort, limit
                .where(newFamily.createdAt.loe(baseCreatedAt)) // ms까지 짤리기 때문에 loe 해도 괜찮음
                .orderBy(newFamily.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    private Map<Long, List<NewFamilyAttendanceItem>> findAttendancesByIdInAndDateRange(List<Long> newFamilyIds, LocalDate startDate, LocalDate endDate) {
        List<NewFamilyAttendanceItem> attendanceItems = queryFactory.select(Projections.constructor(NewFamilyAttendanceItem.class,
                        newFamilyAttendance.date,
                        newFamilyAttendance.status,
                        newFamilyAttendance.reason,
                        newFamilyAttendance.newFamilyId
                ))
                .from(newFamilyAttendance)
                .where(newFamilyAttendance.newFamilyId.in(newFamilyIds),
                        newFamilyAttendance.date.goe(startDate),
                        newFamilyAttendance.date.loe(endDate))
                .fetch();

        return attendanceItems.stream()
                .collect(Collectors.groupingBy(NewFamilyAttendanceItem::getNewFamilyId));
    }
}
