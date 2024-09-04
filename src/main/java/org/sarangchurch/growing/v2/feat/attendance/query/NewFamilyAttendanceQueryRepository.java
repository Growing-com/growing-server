package org.sarangchurch.growing.v2.feat.attendance.query;

import com.mysema.commons.lang.Pair;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.attendance.domain.newfamilyattendance.QNewFamilyAttendance.newFamilyAttendance;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<NewFamilyAttendance> findPromotedIncludedByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Pair<List<NewFamilyAttendance>, Long> p = this.findNewFamilies(pageable.getOffset(), pageable.getPageSize());
        List<NewFamilyAttendance> newFamilyAttendances = p.getFirst();
        Long totalCount = p.getSecond();

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

        return new PageImpl<>(newFamilyAttendances, pageable, totalCount);
    }

    private Pair<List<NewFamilyAttendance>, Long> findNewFamilies(long offset, long limit) {
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

        List<NewFamilyAttendance> content = queryFactory.select(Projections.constructor(NewFamilyAttendance.class,
                        newFamily.id,
                        newFamily.createdAt,
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
                // order, offset, limit
                .orderBy(newFamily.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        Long totalCount = queryFactory
                .select(newFamily.id.count())
                .from(newFamily)
                .fetchOne();

        return Pair.of(content, totalCount);
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
