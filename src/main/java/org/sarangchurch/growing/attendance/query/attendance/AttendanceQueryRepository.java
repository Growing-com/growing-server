package org.sarangchurch.growing.attendance.query.attendance;

import com.mysema.commons.lang.Pair;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.QUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.attendance.domain.attendance.QAttendance.attendance;
import static org.sarangchurch.growing.term.domain.team.QTeam.team;
import static org.sarangchurch.growing.term.domain.team.QTeamMember.teamMember;
import static org.sarangchurch.growing.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class AttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<AttendanceSearchResult> findByUserNamePrefixOrUserAge(AttendanceSearchCond cond, Pageable pageable) {
        QUserEntity user = new QUserEntity("user");
        QUserEntity leader = new QUserEntity("leader");
        QUserEntity manager = new QUserEntity("manager");

        Pair<List<Long>, Long> pair = getUserIdsBySearchCond(cond, pageable);
        List<Long> userIds = pair.getFirst();
        Long totalCount = pair.getSecond();

        List<AttendanceRow> attendances = queryFactory
                .select(Projections.constructor(AttendanceRow.class,
                                leader.name.as("leaderName"),
                                manager.name.as("managerName"),
                                user.id.as("userId"),
                                user.name.as("userName"),
                                user.grade.as("grade"),
                                user.sex.as("sex"),
                                attendance.id.as("attendanceId"),
                                attendance.status.as("status"),
                                attendance.week.as("week"),
                                attendance.etc.as("etc")))
                .from(team)
                .join(teamMember).on(teamMember.teamId.eq(team.id), teamMember.memberId.in(userIds))
                .leftJoin(attendance).on(
                        attendance.teamMemberId.eq(teamMember.id),
                        attendance.week.week.between(cond.getStartDate(), cond.getEndDate()))
                .join(user).on(user.id.eq(teamMember.memberId))
                .join(leader).on(leader.id.eq(team.leaderId))
                .join(manager).on(manager.id.eq(team.managerId))
                .fetch();

        List<AttendanceSearchResult> content = groupByUserId(attendances);

        return new PageImpl<>(content, pageable, totalCount);
    }

    private Pair<List<Long>, Long> getUserIdsBySearchCond(AttendanceSearchCond cond, Pageable pageable) {
        List<Long> userIds = queryFactory
                .select(userEntity.id)
                .from(userEntity)
                .where(userNameStartsWith(cond.getName()), userGradeEq(cond.getGrade()))
                .orderBy(userEntity.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(userEntity.count())
                .from(userEntity)
                .where(userNameStartsWith(cond.getName()), userGradeEq(cond.getGrade()))
                .fetchOne();

        return Pair.of(userIds, totalCount);
    }

    private BooleanExpression userNameStartsWith(String name) {
        if (name == null) {
            return null;
        }

        return userEntity.name.startsWith(name);
    }

    private BooleanExpression userGradeEq(Integer grade) {
        if (grade == null) {
            return null;
        }

        return userEntity.grade.eq(grade);
    }

    public Page<AttendanceSearchResult> findByCody(AttendanceSearchCond cond, Pageable pageable) {
        QUserEntity user = new QUserEntity("user");
        QUserEntity leader = new QUserEntity("leader");
        QUserEntity manager = new QUserEntity("manager");

        Pair<List<Long>, Long> pair = getUserIdsByCodies(cond.getCodyId(), pageable);
        List<Long> userIds = pair.getFirst();
        Long totalCount = pair.getSecond();

        List<AttendanceRow> attendances = queryFactory
                .select(Projections.constructor(AttendanceRow.class,
                        leader.name.as("leaderName"),
                        manager.name.as("managerName"),
                        user.id.as("userId"),
                        user.name.as("userName"),
                        user.grade.as("grade"),
                        user.sex.as("sex"),
                        attendance.id.as("attendanceId"),
                        attendance.status.as("status"),
                        attendance.week.as("week"),
                        attendance.etc.as("etc")
                ))
                .from(team)
                .join(teamMember).on(teamMember.teamId.eq(team.id), teamMember.memberId.in(userIds))
                .leftJoin(attendance).on(
                        attendance.teamMemberId.eq(teamMember.id),
                        attendance.week.week.between(cond.getStartDate(), cond.getEndDate()))
                .join(user).on(user.id.eq(teamMember.memberId))
                .join(leader).on(leader.id.eq(team.leaderId))
                .join(manager).on(manager.id.eq(team.managerId))
                .fetch();

        List<AttendanceSearchResult> content = groupByUserId(attendances);

        return new PageImpl<>(content, pageable, totalCount);
    }

    private Pair<List<Long>, Long> getUserIdsByCodies(List<Long> codyIds, Pageable pageable) {
        QUserEntity manager = new QUserEntity("manager");
        QUserEntity user = new QUserEntity("user");

        List<Long> userIds = queryFactory
                .select(teamMember.memberId)
                .from(term)
                .join(team).on(team.termId.eq(term.id), term.isActive.isTrue())
                .join(manager).on(team.managerId.eq(manager.id), team.managerId.in(codyIds))
                .join(teamMember).on(teamMember.teamId.eq(team.id))
                .join(user).on(teamMember.memberId.eq(user.id))
                .orderBy(user.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(teamMember.memberId.count())
                .from(term)
                .join(team).on(team.termId.eq(term.id), term.isActive.isTrue())
                .join(manager).on(team.managerId.eq(manager.id), team.managerId.in(codyIds))
                .join(teamMember).on(teamMember.teamId.eq(team.id))
                .fetchOne();

        return Pair.of(userIds, totalCount);
    }

    public Page<AttendanceSearchResult> findNewOnly(AttendanceSearchCond cond, Pageable pageable) {
        QUserEntity user = new QUserEntity("user");
        QUserEntity leader = new QUserEntity("leader");
        QUserEntity manager = new QUserEntity("manager");

        Pair<List<Long>, Long> pair = getNewComerIdsByTerm(1L, pageable);
        List<Long> newComerIds = pair.getFirst();
        Long totalCount = pair.getSecond();

        List<AttendanceRow> attendances = queryFactory
                .select(Projections.constructor(AttendanceRow.class,
                        leader.name.as("leaderName"),
                        manager.name.as("managerName"),
                        user.id.as("userId"),
                        user.name.as("userName"),
                        user.grade.as("grade"),
                        user.sex.as("sex"),
                        attendance.id.as("attendanceId"),
                        attendance.status.as("status"),
                        attendance.week.as("week"),
                        attendance.etc.as("etc")
                ))
                .from(team)
                .join(teamMember).on(teamMember.teamId.eq(team.id), teamMember.memberId.in(newComerIds))
                .leftJoin(attendance).on(
                        attendance.teamMemberId.eq(teamMember.id),
                        attendance.week.week.between(cond.getStartDate(), cond.getEndDate()))
                .join(user).on(user.id.eq(teamMember.memberId))
                .join(leader).on(leader.id.eq(team.leaderId))
                .join(manager).on(manager.id.eq(team.managerId))
                .fetch();

        List<AttendanceSearchResult> content = groupByUserId(attendances);

        return new PageImpl<>(content, pageable, totalCount);
    }

    private List<AttendanceSearchResult> groupByUserId(List<AttendanceRow> attendances) {
        // Group by userId
        Map<Long, AttendanceSearchResult> userIdToAttendance = attendances.stream()
                .collect(Collectors.toMap(
                        AttendanceRow::getUserId,
                        attendance -> {
                            AttendanceSearchResult row = AttendanceSearchResult.builder()
                                    .leaderName(attendance.getLeaderName())
                                    .managerName(attendance.getManagerName())
                                    .userId(attendance.getUserId())
                                    .userName(attendance.getUserName())
                                    .grade(attendance.getGrade())
                                    .sex(attendance.getSex())
                                    .build();

                            row.getAttendanceItems().add(
                                    AttendanceSearchResult.AttendanceItem.builder()
                                            .attendanceId(attendance.getAttendanceId())
                                            .status(attendance.getStatus())
                                            .week(attendance.getWeek())
                                            .etc(attendance.getEtc())
                                            .build()
                            );

                            return row;
                        },
                        (prev, curr) -> {
                            curr.getAttendanceItems().addAll(prev.getAttendanceItems());
                            return curr;
                        }
                ));

        // Sort inner attendanceItems by week ASC
        Comparator<AttendanceSearchResult.AttendanceItem> attendanceItemComparator = Comparator.comparing(
                AttendanceSearchResult.AttendanceItem::getWeek,
                Comparator.nullsLast(Comparator.naturalOrder())
        );

        userIdToAttendance.values()
                .forEach(el -> el.getAttendanceItems().sort(attendanceItemComparator));

        // Sort outer attendances by userId ASC
        List<AttendanceSearchResult> result = new ArrayList<>(userIdToAttendance.values());
        result.sort(Comparator.comparing(AttendanceSearchResult::getUserName));

        return result;
    }

    private Pair<List<Long>, Long> getNewComerIdsByTerm(Long termId, Pageable pageable) {
        List<Long> newComerIds = queryFactory.select(userEntity.id)
                .from(userEntity)
                .where(userEntity.visitTermId.eq(termId), userEntity.isActive.eq(true))
                .orderBy(userEntity.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory.select(userEntity.id.count())
                .from(userEntity)
                .where(userEntity.visitTermId.eq(termId), userEntity.isActive.eq(true))
                .fetchOne();

        return Pair.of(newComerIds, totalCount);
    }

    public List<CodyWeeklyAttendanceResult> findByCodyAndWeek(Long codyId, Week week) {
        QUserEntity user = new QUserEntity("user");
        QUserEntity leader = new QUserEntity("leader");
        QUserEntity manager = new QUserEntity("manager");

        return queryFactory
                .select(Projections.constructor(CodyWeeklyAttendanceResult.class,
                        leader.name.as("leaderName"),
                        team.id.as("teamId"),
                        teamMember.id.as("teamMemberId"),
                        user.name.as("userName"),
                        user.grade.as("grade"),
                        user.sex.as("sex"),
                        attendance.id.as("attendanceId"),
                        attendance.status.as("status"),
                        attendance.week.as("week"),
                        attendance.etc.as("etc")
                ))
                .from(team)
                .join(teamMember).on(teamMember.teamId.eq(team.id))
                .leftJoin(attendance).on(attendance.teamMemberId.eq(teamMember.id), attendance.week.eq(week))
                .join(user).on(user.id.eq(teamMember.memberId))
                .join(leader).on(leader.id.eq(team.leaderId))
                .join(manager).on(manager.id.eq(team.managerId), manager.id.eq(codyId))
                .orderBy(team.id.asc())
                .fetch();
    }
}
