package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.query.model.AttendanceSearchListItem;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.attendance.QAttendance.attendance;
import static org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.QStumpAttendance.stumpAttendance;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class AttendanceSearchQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<AttendanceSearchListItem> search(LocalDate startDate, LocalDate endDate) {
        QUser codyUser = new QUser("codyUser");
        QUser leaderUser = new QUser("leaderUser");

        Term activeTerm = queryFactory.select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        // 교역자
        String pastorName = queryFactory.select(user.name)
                .from(pastor)
                .join(user).on(
                        user.id.eq(pastor.userId),
                        pastor.termId.eq(activeTerm.getId()),
                        pastor.isSenior.isTrue()
                )
                .fetchOne();

        // 코디
        List<Tuple> codyTuple = queryFactory.select(
                        user.id,
                        user.name,
                        user.sex,
                        user.grade
                )
                .from(cody)
                .join(user).on(
                        user.id.eq(cody.userId),
                        cody.termId.eq(activeTerm.getId())
                )
                .fetch();

        // 일반 순장
        List<Tuple> smallGroupLeaderTuples = queryFactory.select(
                        smallGroup.leaderUserId,
                        user.name,
                        user.sex,
                        user.grade,
                        codyUser.name
                )
                .from(smallGroup)
                .join(user).on(
                        user.id.eq(smallGroup.leaderUserId),
                        smallGroup.termId.eq(activeTerm.getId())
                )
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();

        // 일반 순원
        List<Tuple> smallGroupMemberTuples = queryFactory.select(
                        user.id,
                        user.name,
                        user.sex,
                        user.grade,
                        codyUser.name,
                        leaderUser.name
                )
                .from(smallGroupMember)
                .join(smallGroup).on(
                        smallGroup.id.eq(smallGroupMember.smallGroupId),
                        smallGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroup.leaderUserId))
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();

        // 새가족 순장
        List<Tuple> newFamilyGroupLeaderTuples = queryFactory.select(
                        newFamilyGroup.leaderUserId,
                        user.name,
                        user.sex,
                        user.grade,
                        codyUser.name
                )
                .from(newFamilyGroup)
                .join(user).on(
                        user.id.eq(newFamilyGroup.leaderUserId),
                        newFamilyGroup.termId.eq(activeTerm.getId())
                )
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();

        // 새가족 순원
        List<Tuple> newFamilyGroupMemberTuples = queryFactory.select(
                        user.id,
                        user.name,
                        user.sex,
                        user.grade,
                        codyUser.name,
                        leaderUser.name
                )
                .from(newFamilyGroupMember)
                .join(newFamilyGroup).on(
                        newFamilyGroup.id.eq(newFamilyGroupMember.newFamilyGroupId),
                        newFamilyGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroup.leaderUserId))
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();

        // 출석 데이터
        List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> stumpAttendances = queryFactory.select(Projections.constructor(AttendanceSearchListItem.AttendanceSearchListItemAttendItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        stumpAttendance.status.as("status"),
                        stumpAttendance.date.as("date"),
                        stumpAttendance.reason.as("reason")
                ))
                .from(stumpAttendance)
                .join(user).on(
                        user.id.eq(stumpAttendance.userId),
                        stumpAttendance.date.between(startDate, endDate)
                )
                .fetch();

        List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> normalAttendances = queryFactory.select(Projections.constructor(AttendanceSearchListItem.AttendanceSearchListItemAttendItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        attendance.status.as("status"),
                        attendance.date.as("date"),
                        attendance.reason.as("reason")
                ))
                .from(attendance)
                .join(user).on(
                        user.id.eq(attendance.userId),
                        attendance.date.between(startDate, endDate)
                )
                .fetch();

        List<AttendanceSearchListItem> result = new ArrayList<>();

        List<LocalDate> dateRange = getDateRange(startDate, endDate);

        result.addAll(mapCodyTuples(pastorName, codyTuple, stumpAttendances, dateRange));
        result.addAll(mapLeaderTuples(smallGroupLeaderTuples, normalAttendances, dateRange));
        result.addAll(mapMemberTuples(smallGroupMemberTuples, normalAttendances, dateRange));
        result.addAll(mapLeaderTuples(newFamilyGroupLeaderTuples, normalAttendances, dateRange));
        result.addAll(mapMemberTuples(newFamilyGroupMemberTuples, normalAttendances, dateRange));

        result.sort(Comparator.comparing(AttendanceSearchListItem::getCodyName)
                .thenComparing(AttendanceSearchListItem::getLeaderName));

        return result;
    }

    private List<AttendanceSearchListItem> mapMemberTuples(
            List<Tuple> memberTuples,
            List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> normalAttendances,
            List<LocalDate> dateRange
    ) {
        return memberTuples.stream()
                .map(tup -> {
                    Long userId = tup.get(0, Long.class);
                    String name = tup.get(1, String.class);
                    Sex sex = tup.get(2, Sex.class);
                    Integer grade = tup.get(3, Integer.class);
                    String codyName = tup.get(4, String.class);
                    String leaderName = tup.get(5, String.class);

                    List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> items = dateRange.stream()
                            .map(date -> {
                                Optional<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> op = normalAttendances.stream()
                                        .filter(it -> it.matchByUserAndDate(userId, date))
                                        .findFirst();

                                if (op.isPresent()) {
                                    AttendanceSearchListItem.AttendanceSearchListItemAttendItem item = op.get();

                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            item.getStatus(),
                                            item.getDate(),
                                            item.getReason()
                                    );
                                } else {
                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            AttendanceStatus.NONE,
                                            date,
                                            ""
                                    );
                                }
                            })
                            .collect(Collectors.toList());

                    return new AttendanceSearchListItem(
                            userId,
                            name,
                            sex,
                            grade,
                            codyName,
                            leaderName,
                            items
                    );
                })
                .collect(Collectors.toList());
    }

    private List<AttendanceSearchListItem> mapLeaderTuples(
            List<Tuple> leaderTuples,
            List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> normalAttendances,
            List<LocalDate> dateRange
    ) {
        return leaderTuples.stream()
                .map(tup -> {
                    Long userId = tup.get(0, Long.class);
                    String name = tup.get(1, String.class);
                    Sex sex = tup.get(2, Sex.class);
                    Integer grade = tup.get(3, Integer.class);
                    String codyName = tup.get(4, String.class);

                    List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> items = dateRange.stream()
                            .map(date -> {
                                Optional<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> op = normalAttendances.stream()
                                        .filter(it -> it.matchByUserAndDate(userId, date))
                                        .findFirst();

                                if (op.isPresent()) {
                                    AttendanceSearchListItem.AttendanceSearchListItemAttendItem item = op.get();

                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            item.getStatus(),
                                            item.getDate(),
                                            item.getReason()
                                    );
                                } else {
                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            AttendanceStatus.NONE,
                                            date,
                                            ""
                                    );
                                }
                            })
                            .collect(Collectors.toList());

                    return new AttendanceSearchListItem(
                            userId,
                            name,
                            sex,
                            grade,
                            codyName,
                            name,
                            items
                    );
                })
                .collect(Collectors.toList());
    }

    private List<AttendanceSearchListItem> mapCodyTuples(
            String pastorName,
            List<Tuple> codyTuple,
            List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> stumpAttendances,
            List<LocalDate> dateRange
    ) {
        return codyTuple.stream()
                .map(tup -> {
                    Long userId = tup.get(0, Long.class);
                    String name = tup.get(1, String.class);
                    Sex sex = tup.get(2, Sex.class);
                    Integer grade = tup.get(3, Integer.class);

                    List<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> items = dateRange.stream()
                            .map(date -> {
                                Optional<AttendanceSearchListItem.AttendanceSearchListItemAttendItem> op = stumpAttendances.stream()
                                        .filter(it -> it.matchByUserAndDate(userId, date))
                                        .findAny();

                                if (op.isPresent()) {
                                    AttendanceSearchListItem.AttendanceSearchListItemAttendItem item = op.get();

                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            item.getStatus(),
                                            item.getDate(),
                                            item.getReason()
                                    );
                                } else {
                                    return new AttendanceSearchListItem.AttendanceSearchListItemAttendItem(
                                            userId,
                                            name,
                                            sex,
                                            grade,
                                            AttendanceStatus.NONE,
                                            date,
                                            ""
                                    );
                                }
                            })
                            .collect(Collectors.toList());

                    return new AttendanceSearchListItem(
                            userId,
                            name,
                            sex,
                            grade,
                            pastorName,
                            name,
                            items
                    );
                })
                .collect(Collectors.toList());
    }

    private List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
        // startDate가 일요일이 아닐 경우, 다음 일요일로 이동
        List<LocalDate> sundays = new ArrayList<>();
        LocalDate currentDate = startDate;

        // startDate가 일요일이 아니면 가장 가까운 일요일로 이동
        if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currentDate = currentDate.with(DayOfWeek.SUNDAY);
        }

        // 일요일이 endDate 이후가 될 때까지 반복
        while (!currentDate.isAfter(endDate)) {
            if (!currentDate.isBefore(startDate)) {  // startDate 이후일 때만 추가
                sundays.add(currentDate);
            }
            currentDate = currentDate.plusWeeks(1);  // 매주 일요일을 추가
        }

        // 최신순으로 정렬 (오름차순)
        sundays.sort(Comparator.reverseOrder());

        return sundays;
    }
}
