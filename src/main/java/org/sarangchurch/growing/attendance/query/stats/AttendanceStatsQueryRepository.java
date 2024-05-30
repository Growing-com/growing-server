package org.sarangchurch.growing.attendance.query.stats;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendance;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyAttendance.weeklyAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyPersonalAttendance.weeklyPersonalAttendance;
import static org.sarangchurch.growing.term.domain.team.QTeam.team;
import static org.sarangchurch.growing.term.domain.team.QTeamMember.teamMember;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class AttendanceStatsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<WeeklyPersonalAttendanceSummaryResult> findAbsentByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Long> absentUserIds = queryFactory
                .select(weeklyPersonalAttendance.userId)
                .from(weeklyPersonalAttendance)
                .where(weeklyPersonalAttendance.status.eq(AttendanceStatus.ABSENT),
                        weeklyPersonalAttendance.week.week.between(startDate, endDate))
                .fetch();

        List<WeeklyPersonalAttendanceSummaryRow> attendances = queryFactory
                .select(Projections.constructor(WeeklyPersonalAttendanceSummaryRow.class,
                        weeklyPersonalAttendance.leaderName.as("leaderName"),
                        weeklyPersonalAttendance.managerName.as("managerName"),
                        weeklyPersonalAttendance.userId.as("userId"),
                        weeklyPersonalAttendance.userName.as("userName"),
                        weeklyPersonalAttendance.userPhone.as("userPhone"),
                        weeklyPersonalAttendance.userGrade.as("grade"),
                        weeklyPersonalAttendance.userSex.as("sex"),
                        weeklyPersonalAttendance.attendanceId.as("attendanceId"),
                        weeklyPersonalAttendance.status.as("status"),
                        weeklyPersonalAttendance.week.as("week"),
                        weeklyPersonalAttendance.etc.as("etc")
                ))
                .from(weeklyPersonalAttendance)
                .where(weeklyPersonalAttendance.userId.in(absentUserIds))
                .fetch();

        return groupByUserId(attendances);
    }

    public List<WeeklyPersonalAttendanceSummaryResult> findNewOnlyByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Long> currentNewFamilyIds = queryFactory
                .select(teamMember.memberId)
                .from(teamMember)
                .where(teamMember.duty.eq(Duty.NEW_COMER))
                .fetch();

        List<WeeklyPersonalAttendanceSummaryRow> attendances = queryFactory
                .select(Projections.constructor(WeeklyPersonalAttendanceSummaryRow.class,
                        weeklyPersonalAttendance.leaderName.as("leaderName"),
                        weeklyPersonalAttendance.managerName.as("managerName"),
                        weeklyPersonalAttendance.userId.as("userId"),
                        weeklyPersonalAttendance.userName.as("userName"),
                        weeklyPersonalAttendance.userPhone.as("userPhone"),
                        weeklyPersonalAttendance.userGrade.as("grade"),
                        weeklyPersonalAttendance.userSex.as("sex"),
                        weeklyPersonalAttendance.attendanceId.as("attendanceId"),
                        weeklyPersonalAttendance.status.as("status"),
                        weeklyPersonalAttendance.week.as("week"),
                        weeklyPersonalAttendance.etc.as("etc")
                ))
                .from(weeklyPersonalAttendance)
                .join(userEntity).on(
                        userEntity.id.eq(weeklyPersonalAttendance.userId),
                        userEntity.id.in(currentNewFamilyIds),
                        weeklyPersonalAttendance.week.week.between(startDate, endDate)
                )
                .fetch();

        return groupByUserId(attendances);
    }

    private List<WeeklyPersonalAttendanceSummaryResult> groupByUserId(List<WeeklyPersonalAttendanceSummaryRow> attendances) {
        // Group by user
        Map<Long, WeeklyPersonalAttendanceSummaryResult> userIdToAttendance = attendances.stream()
                .collect(Collectors.toMap(
                        WeeklyPersonalAttendanceSummaryRow::getUserId,
                        attendance -> {
                            WeeklyPersonalAttendanceSummaryResult row = WeeklyPersonalAttendanceSummaryResult.builder()
                                    .leaderName(attendance.getLeaderName())
                                    .managerName(attendance.getManagerName())
                                    .userId(attendance.getUserId())
                                    .userName(attendance.getUserName())
                                    .userPhone(attendance.getUserPhone())
                                    .grade(attendance.getGrade())
                                    .sex(attendance.getSex())
                                    .build();

                            row.getAttendanceItems().add(
                                    WeeklyPersonalAttendanceSummaryResult.AttendanceItem.builder()
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
        userIdToAttendance.values()
                .forEach(el -> el.getAttendanceItems().sort((a, b) -> a.getWeek().isBefore(b.getWeek()) ? -1 : 1));

        // Sort outer attendances by userName ASC
        List<WeeklyPersonalAttendanceSummaryResult> result = new ArrayList<>(userIdToAttendance.values());
        result.sort(comparing(WeeklyPersonalAttendanceSummaryResult::getUserName));

        return result;
    }

    public AttendanceProgressResult getAttendanceProgressByTermAndWeek(Long termId, Week week) {
        Long totalRegistered = queryFactory
                .select(userEntity.count())
                .from(userEntity)
                .where(userEntity.visitDate.loe(week.getWeek()), userEntity.isActive.isTrue())
                .fetchOne();

        List<WeeklyPersonalAttendance> attendances = queryFactory
                .select(weeklyPersonalAttendance)
                .from(weeklyPersonalAttendance)
                .where(weeklyPersonalAttendance.week.eq(week))
                .fetch();

        Long totalProgressed = (long) attendances.size();

        List<Long> progressedLeaderIds = attendances.stream()
                .map(WeeklyPersonalAttendance::getLeaderId)
                .distinct()
                .collect(Collectors.toList());

        List<AttendanceProgressResult.NotProgressedLeaders> notProgressedLeaderNames = queryFactory
                .select(Projections.constructor(AttendanceProgressResult.NotProgressedLeaders.class,
                        userEntity.id,
                        userEntity.name
                ))
                .from(team)
                .join(userEntity).on(userEntity.id.eq(team.leaderId))
                .where(team.termId.eq(termId), team.leaderId.notIn(progressedLeaderIds))
                .fetch();

        return new AttendanceProgressResult(week, totalRegistered, totalProgressed, notProgressedLeaderNames);
    }

    public List<WeeklyAttendanceSummaryResult> findSummaryByWeek(LocalDate start, LocalDate end) {
        return queryFactory
                .select(Projections.constructor(WeeklyAttendanceSummaryResult.class,
                        weeklyAttendance.week.as("week"),
                        weeklyAttendance.attendanceEtc.totalRegistered.as("totalRegistered"),
                        weeklyAttendance.attendanceEtc.totalAttendance.as("totalAttendance"),
                        weeklyAttendance.attendanceEtc.totalOnline.as("totalOnline"),
                        weeklyAttendance.attendanceEtc.totalAbsent.as("totalAbsent"),
                        weeklyAttendance.attendanceEtc.maleRegistered.as("maleRegistered"),
                        weeklyAttendance.attendanceEtc.maleAttendance.as("maleAttendance"),
                        weeklyAttendance.attendanceEtc.femaleRegistered.as("femaleRegistered"),
                        weeklyAttendance.attendanceEtc.femaleAttendance.as("femaleAttendance"),
                        weeklyAttendance.attendanceEtc.newComerRegistered.as("newComerRegistered"),
                        weeklyAttendance.attendanceEtc.newComerAttendance.as("newComerAttendance"),
                        weeklyAttendance.attendanceEtc.newVisited.as("newVisited")
                ))
                .from(weeklyAttendance)
                .where(weeklyAttendance.week.week.goe(start), weeklyAttendance.week.week.loe(end))
                .orderBy(weeklyAttendance.week.week.asc())
                .fetch();
    }

}
