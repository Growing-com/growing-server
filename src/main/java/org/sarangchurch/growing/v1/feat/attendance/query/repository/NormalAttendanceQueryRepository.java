package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.query.model.NormalAttendanceListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.attendance.QAttendance.attendance;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class NormalAttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NormalAttendanceListItem> findByCodyAndDate(Long codyId, LocalDate date) {
        QUser leaderUser = new QUser("leaderUser");

        // 코디
        String codyName = queryFactory.select(user.name)
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.id.eq(codyId))
                .fetchOne();

        assert codyName != null;

        // 일반 순장
        List<Tuple> smallGroupLeaderTuples = queryFactory.select(
                        smallGroup.leaderUserId,
                        user.name,
                        user.sex,
                        user.grade
                )
                .from(smallGroup)
                .join(user).on(
                        user.id.eq(smallGroup.leaderUserId),
                        smallGroup.codyId.eq(codyId)
                )
                .fetch();

        // 일반 순원
        List<Tuple> smallGroupMemberTuples = queryFactory.select(
                        user.id,
                        user.name,
                        user.sex,
                        user.grade,
                        leaderUser.name
                )
                .from(smallGroup)
                .join(smallGroupMember).on(
                        smallGroupMember.smallGroupId.eq(smallGroup.id),
                        smallGroup.codyId.eq(codyId)
                )
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroup.leaderUserId))
                .fetch();

        // 새가족 순장
        List<Tuple> newFamilyGroupLeaderTuples = queryFactory.select(
                        newFamilyGroup.leaderUserId,
                        user.name,
                        user.sex,
                        user.grade
                )
                .from(newFamilyGroup)
                .join(user).on(
                        user.id.eq(newFamilyGroup.leaderUserId),
                        newFamilyGroup.codyId.eq(codyId)
                )
                .fetch();

        // 새가족 순원
        List<Tuple> newFamilyGroupMemberTuples = queryFactory.select(
                        user.id,
                        user.name,
                        user.sex,
                        user.grade,
                        leaderUser.name
                )
                .from(newFamilyGroup)
                .join(newFamilyGroupMember).on(
                        newFamilyGroupMember.newFamilyGroupId.eq(newFamilyGroup.id),
                        newFamilyGroup.codyId.eq(codyId)
                )
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroup.leaderUserId))
                .fetch();

        List<Long> userIds = new ArrayList<>();

        userIds.addAll(smallGroupLeaderTuples.stream().map(it -> it.get(0, Long.class)).collect(Collectors.toList()));
        userIds.addAll(smallGroupMemberTuples.stream().map(it -> it.get(0, Long.class)).collect(Collectors.toList()));
        userIds.addAll(newFamilyGroupLeaderTuples.stream().map(it -> it.get(0, Long.class)).collect(Collectors.toList()));
        userIds.addAll(newFamilyGroupMemberTuples.stream().map(it -> it.get(0, Long.class)).collect(Collectors.toList()));

        List<NormalAttendanceListItem.NormalAttendanceListItemAttendItem> attendItems = queryFactory.select(Projections.constructor(NormalAttendanceListItem.NormalAttendanceListItemAttendItem.class,
                        user.id.as("userId"),
                        attendance.status.as("status"),
                        attendance.date.as("date"),
                        attendance.reason.as("reason")
                ))
                .from(attendance)
                .join(user).on(
                        user.id.eq(attendance.userId),
                        user.id.in(userIds),
                        attendance.date.eq(date)
                )
                .fetch();

        List<NormalAttendanceListItem> result = new ArrayList<>();

        result.addAll(mapLeaderTuples(codyName, date, smallGroupLeaderTuples, attendItems));
        result.addAll(mapMemberTuples(codyName, date, smallGroupMemberTuples, attendItems));
        result.addAll(mapLeaderTuples(codyName, date, newFamilyGroupLeaderTuples, attendItems));
        result.addAll(mapMemberTuples(codyName, date, newFamilyGroupMemberTuples, attendItems));

        result.sort(Comparator.comparing(
                NormalAttendanceListItem::getLeaderName)
                .thenComparing(NormalAttendanceListItem::getName)
        );

        return result;
    }

    private List<NormalAttendanceListItem> mapLeaderTuples(
            String codyName,
            LocalDate date,
            List<Tuple> newFamilyGroupLeaderTuples,
            List<NormalAttendanceListItem.NormalAttendanceListItemAttendItem> attendItems
    ) {
        return newFamilyGroupLeaderTuples.stream()
                .map(tup -> {
                    Long userId = tup.get(0, Long.class);
                    String name = tup.get(1, String.class);
                    Sex sex = tup.get(2, Sex.class);
                    Integer grade = tup.get(3, Integer.class);

                    Optional<NormalAttendanceListItem.NormalAttendanceListItemAttendItem> op = attendItems.stream()
                            .filter(attendItem -> attendItem.getUserId().equals(userId))
                            .findAny();

                    if (op.isPresent()) {
                        NormalAttendanceListItem.NormalAttendanceListItemAttendItem item = op.get();

                        return new NormalAttendanceListItem(
                                userId,
                                name,
                                sex,
                                grade,
                                codyName,
                                name,
                                List.of(item)
                        );
                    } else {
                        NormalAttendanceListItem.NormalAttendanceListItemAttendItem emptyAttendance = new NormalAttendanceListItem.NormalAttendanceListItemAttendItem(
                                userId,
                                AttendanceStatus.NONE,
                                date,
                                ""
                        );

                        return new NormalAttendanceListItem(
                                userId,
                                name,
                                sex,
                                grade,
                                codyName,
                                name,
                                List.of(emptyAttendance)
                        );
                    }

                })
                .collect(Collectors.toList());
    }

    private List<NormalAttendanceListItem> mapMemberTuples(
            String codyName,
            LocalDate date,
            List<Tuple> smallGroupMemberTuples,
            List<NormalAttendanceListItem.NormalAttendanceListItemAttendItem> attendItems
    ) {
        return smallGroupMemberTuples.stream()
                .map(tup -> {
                    Long userId = tup.get(0, Long.class);
                    String name = tup.get(1, String.class);
                    Sex sex = tup.get(2, Sex.class);
                    Integer grade = tup.get(3, Integer.class);
                    String leaderName = tup.get(4, String.class);

                    Optional<NormalAttendanceListItem.NormalAttendanceListItemAttendItem> op = attendItems.stream()
                            .filter(attendItem -> attendItem.getUserId().equals(userId))
                            .findAny();

                    if (op.isPresent()) {
                        NormalAttendanceListItem.NormalAttendanceListItemAttendItem item = op.get();

                        return new NormalAttendanceListItem(
                                userId,
                                name,
                                sex,
                                grade,
                                codyName,
                                leaderName,
                                List.of(item)
                        );
                    } else {
                        NormalAttendanceListItem.NormalAttendanceListItemAttendItem emptyAttendance = new NormalAttendanceListItem.NormalAttendanceListItemAttendItem(
                                userId,
                                AttendanceStatus.NONE,
                                date,
                                ""
                        );

                        return new NormalAttendanceListItem(
                                userId,
                                name,
                                sex,
                                grade,
                                codyName,
                                leaderName,
                                List.of(emptyAttendance)
                        );
                    }

                })
                .collect(Collectors.toList());
    }
}
