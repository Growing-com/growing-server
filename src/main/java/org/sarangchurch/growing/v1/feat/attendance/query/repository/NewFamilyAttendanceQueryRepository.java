package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.query.model.NewFamilyAttendanceListItem;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.QNewFamilyAttendance.newFamilyAttendance;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyAttendanceListItem> findNewFamilyAttendancesByNewFamilyGroup(Long newFamilyGroupId) {
        List<LocalDate> lastTwelveSundays = getLastTwelveSundays();

        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem> items = queryFactory
                .select(Projections.constructor(NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        newFamilyAttendance.status.as("status"),
                        newFamilyAttendance.date.as("date"),
                        newFamilyAttendance.reason.as("reason")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.newFamilyGroupId.eq(newFamilyGroupId),
                        newFamily.status.ne(NewFamilyStatus.PROMOTED)
                )
                // 새가족반 리더
                .leftJoin(newFamilyGroup).on(newFamily.newFamilyGroupId.eq(newFamilyGroup.id))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 출석
                .leftJoin(newFamilyAttendance).on(newFamily.id.eq(newFamilyAttendance.newFamilyId))
                .fetch();

        Map<Long, List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem>> groupedById = items.stream()
                .collect(Collectors.groupingBy(NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem::getNewFamilyId));

        return groupedById.values()
                .stream()
                .map(attendItems -> {
                    long attendCount = attendItems.stream().filter(it -> it.getStatus() == AttendanceStatus.ATTEND).count();
                    long absentCount = attendItems.stream().filter(it -> it.getStatus() == AttendanceStatus.ABSENT).count();

                    NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem firstItem = attendItems.get(0);

                    List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem> sortedByDateDesc = lastTwelveSundays.stream()
                            .map(date -> attendItems.stream()
                                    .filter(attendItem -> date.equals(attendItem.getDate()))
                                    .findAny()
                                    .orElse(new NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem(
                                            firstItem.getNewFamilyId(),
                                            firstItem.getName(),
                                            firstItem.getSex(),
                                            firstItem.getGrade(),
                                            firstItem.getNewFamilyGroupLeaderName(),
                                            AttendanceStatus.NONE,
                                            date,
                                            ""
                                    ))
                            )
                            .collect(Collectors.toList());

                    return new NewFamilyAttendanceListItem(
                            firstItem.getNewFamilyId(),
                            firstItem.getName(),
                            firstItem.getSex(),
                            firstItem.getGrade(),
                            firstItem.getNewFamilyGroupLeaderName(),
                            attendCount,
                            absentCount,
                            sortedByDateDesc
                    );
                })
                .sorted(Comparator.comparing(NewFamilyAttendanceListItem::getNewFamilyGroupLeaderName, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    public List<NewFamilyAttendanceListItem> findNewFamilyAttendances() {
        List<LocalDate> lastTwelveSundays = getLastTwelveSundays();

        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem> items = queryFactory
                .select(Projections.constructor(NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem.class,
                        newFamily.id.as("newFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        newFamilyGroupLeaderUser.name.as("newFamilyGroupLeaderName"),
                        newFamilyAttendance.status.as("status"),
                        newFamilyAttendance.date.as("date"),
                        newFamilyAttendance.reason.as("reason")
                ))
                .from(newFamily)
                .join(user).on(
                        user.id.eq(newFamily.userId),
                        newFamily.status.ne(NewFamilyStatus.PROMOTED)
                )
                // 새가족반 리더
                .leftJoin(newFamilyGroup).on(newFamily.newFamilyGroupId.eq(newFamilyGroup.id))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId))
                // 출석
                .leftJoin(newFamilyAttendance).on(newFamily.id.eq(newFamilyAttendance.newFamilyId))
                .fetch();

        Map<Long, List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem>> groupedById = items.stream()
                .collect(Collectors.groupingBy(NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem::getNewFamilyId));

        return groupedById.values()
                .stream()
                .map(attendItems -> {
                    long attendCount = attendItems.stream().filter(it -> it.getStatus() == AttendanceStatus.ATTEND).count();
                    long absentCount = attendItems.stream().filter(it -> it.getStatus() == AttendanceStatus.ABSENT).count();

                    NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem firstItem = attendItems.get(0);

                    List<NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem> sortedByDateDesc = lastTwelveSundays.stream()
                            .map(date -> attendItems.stream()
                                    .filter(attendItem -> date.equals(attendItem.getDate()))
                                    .findAny()
                                    .orElse(new NewFamilyAttendanceListItem.NewFamilyAttendanceListItemAttendItem(
                                            firstItem.getNewFamilyId(),
                                            firstItem.getName(),
                                            firstItem.getSex(),
                                            firstItem.getGrade(),
                                            firstItem.getNewFamilyGroupLeaderName(),
                                            AttendanceStatus.NONE,
                                            date,
                                            ""
                                    ))
                            )
                            .collect(Collectors.toList());

                    return new NewFamilyAttendanceListItem(
                            firstItem.getNewFamilyId(),
                            firstItem.getName(),
                            firstItem.getSex(),
                            firstItem.getGrade(),
                            firstItem.getNewFamilyGroupLeaderName(),
                            attendCount,
                            absentCount,
                            sortedByDateDesc
                    );
                })
                .sorted(Comparator.comparing(NewFamilyAttendanceListItem::getNewFamilyGroupLeaderName, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    private List<LocalDate> getLastTwelveSundays() {
        List<LocalDate> sundays = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // 최근 일요일부터 시작
        LocalDate sunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        for (int i = 0; i < 12; i++) {
            sundays.add(sunday);
            sunday = sunday.minusWeeks(1); // 이전 주 일요일로 이동
        }

        return sundays;
    }
}
