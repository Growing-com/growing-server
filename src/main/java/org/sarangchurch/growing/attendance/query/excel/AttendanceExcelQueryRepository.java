package org.sarangchurch.growing.attendance.query.excel;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.query.excel.grade.WeeklyGradeAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.grade.WeeklyGradeAttendanceRow;
import org.sarangchurch.growing.attendance.query.excel.leader.WeeklyLeaderAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.leader.WeeklyLeaderAttendanceRow;
import org.sarangchurch.growing.attendance.query.excel.manager.WeeklyManagerAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.manager.WeeklyManagerAttendanceRow;
import org.sarangchurch.growing.attendance.query.excel.personal.WeeklyPersonalAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.personal.WeeklyPersonalAttendanceRow;
import org.sarangchurch.growing.attendance.query.excel.rate.TermPersonalAttendanceRateResult;
import org.sarangchurch.growing.term.domain.term.QTerm;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.user.domain.QUserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static org.sarangchurch.growing.attendance.batch.entity.term.QTermAttendance.termAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.term.QTermPersonalAttendance.termPersonalAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyAttendance.weeklyAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyGradeAttendance.weeklyGradeAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyLeaderAttendance.weeklyLeaderAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyManagerAttendance.weeklyManagerAttendance;
import static org.sarangchurch.growing.attendance.batch.entity.week.QWeeklyPersonalAttendance.weeklyPersonalAttendance;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class AttendanceExcelQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<WeeklyPersonalAttendanceResult> findAllPersonalAttendanceByTerm(Long termId) {
        List<WeeklyPersonalAttendanceRow> weeklyPersonalAttendanceRows = queryFactory
                .select(Projections.constructor(WeeklyPersonalAttendanceRow.class,
                        weeklyPersonalAttendance.managerId.as("managerId"),
                        weeklyPersonalAttendance.managerName.as("managerName"),
                        weeklyPersonalAttendance.leaderName.as("leaderName"),
                        weeklyPersonalAttendance.userId.as("userId"),
                        weeklyPersonalAttendance.userName.as("userName"),
                        weeklyPersonalAttendance.userSex.as("sex"),
                        weeklyPersonalAttendance.userGrade.as("grade"),
                        weeklyPersonalAttendance.userPhone.as("phoneNumber"),
                        weeklyPersonalAttendance.week.as("week"),
                        weeklyPersonalAttendance.status.as("status")
                ))
                .from(weeklyPersonalAttendance)
                .where(weeklyPersonalAttendance.termId.eq(termId))
                .fetch();

        return groupByUserAndSortByManager(weeklyPersonalAttendanceRows);
    }

    public List<WeeklyPersonalAttendanceResult> findAllNewFamilyAttendanceByTerm(Long termId) {
        List<Long> newFamilyIds = queryFactory
                .select(userEntity.id)
                .from(userEntity)
                .where(userEntity.visitTermId.eq(termId))
                .fetch();

        List<WeeklyPersonalAttendanceRow> weeklyPersonalAttendanceRows = queryFactory
                .select(Projections.constructor(WeeklyPersonalAttendanceRow.class,
                        weeklyPersonalAttendance.managerId.as("managerId"),
                        weeklyPersonalAttendance.managerName.as("managerName"),
                        weeklyPersonalAttendance.leaderName.as("leaderName"),
                        weeklyPersonalAttendance.userId.as("userId"),
                        weeklyPersonalAttendance.userName.as("userName"),
                        weeklyPersonalAttendance.userSex.as("sex"),
                        weeklyPersonalAttendance.userGrade.as("grade"),
                        weeklyPersonalAttendance.userPhone.as("phoneNumber"),
                        weeklyPersonalAttendance.week.as("week"),
                        weeklyPersonalAttendance.status.as("status")
                ))
                .from(weeklyPersonalAttendance)
                .where(weeklyPersonalAttendance.userId.in(newFamilyIds),
                        weeklyPersonalAttendance.termId.eq(termId))
                .fetch();

        return groupByUserAndSortByManager(weeklyPersonalAttendanceRows);
    }

    private List<WeeklyPersonalAttendanceResult> groupByUserAndSortByManager(List<WeeklyPersonalAttendanceRow> weeklyPersonalAttendanceRows) {
        // Group by user
        Map<Long, WeeklyPersonalAttendanceResult> userIdToAttendance = weeklyPersonalAttendanceRows.stream()
                .collect(Collectors.toMap(
                        WeeklyPersonalAttendanceRow::getUserId,
                        attendance -> {
                            WeeklyPersonalAttendanceResult weeklyPersonalAttendanceResult = WeeklyPersonalAttendanceResult.builder()
                                    .managerId(attendance.getManagerId())
                                    .managerName(attendance.getManagerName())
                                    .leaderName(attendance.getLeaderName())
                                    .userName(attendance.getUserName())
                                    .sex(attendance.getSex())
                                    .grade(attendance.getGrade())
                                    .phoneNumber(attendance.getPhoneNumber())
                                    .build();

                            weeklyPersonalAttendanceResult.getAttendanceItems().add(
                                    new WeeklyPersonalAttendanceResult.WeeklyPersonalAttendanceItem(attendance.getWeek(), attendance.getStatus())
                            );

                            return weeklyPersonalAttendanceResult;
                        },
                        (prev, curr) -> {
                            curr.getAttendanceItems().addAll(prev.getAttendanceItems());
                            return curr;
                        }
                ));

        // Sort inner attendanceItems by week ASC
        userIdToAttendance.values()
                .forEach(el -> el.getAttendanceItems().sort((a, b) -> a.getWeek().isBefore(b.getWeek()) ? -1 : 1));

        // Sort outer attendances by managerName, leaderName
        List<WeeklyPersonalAttendanceResult> result = new ArrayList<>(userIdToAttendance.values());

        result.sort(comparing(WeeklyPersonalAttendanceResult::getManagerName)
                .thenComparing(WeeklyPersonalAttendanceResult::getLeaderName));

        return result;
    }

    public List<WeeklyLeaderAttendanceResult> findAllLeaderAttendanceByTerm(Long termId) {
        QUserEntity leader = new QUserEntity("leader");

        Term term = queryFactory
                .select(QTerm.term)
                .from(QTerm.term)
                .where(QTerm.term.id.eq(termId))
                .fetchOne();

        List<WeeklyLeaderAttendanceRow> weeklyLeaderAttendanceRows = queryFactory
                .select(Projections.constructor(WeeklyLeaderAttendanceRow.class,
                        weeklyLeaderAttendance.managerId.as("managerId"),
                        weeklyLeaderAttendance.managerName.as("managerName"),
                        weeklyLeaderAttendance.leaderId.as("leaderId"),
                        weeklyLeaderAttendance.leaderName.as("leaderName"),
                        leader.sex.as("sex"),
                        leader.grade.as("grade"),
                        leader.phoneNumber.as("phoneNumber"),
                        weeklyAttendance.week.as("week"),
                        weeklyLeaderAttendance.totalRegistered.as("totalRegistered"),
                        weeklyLeaderAttendance.totalAttendance.as("totalAttendance")
                ))
                .from(weeklyLeaderAttendance)
                .join(leader).on(leader.id.eq(weeklyLeaderAttendance.leaderId))
                .join(weeklyAttendance).on(
                        weeklyAttendance.id.eq(weeklyLeaderAttendance.weeklyAttendanceId),
                        weeklyAttendance.week.week.between(term.getStartDate(), term.getEndDate())
                )
                .fetch();

        return groupByLeaderAndSortByManager(weeklyLeaderAttendanceRows);
    }

    private List<WeeklyLeaderAttendanceResult> groupByLeaderAndSortByManager(List<WeeklyLeaderAttendanceRow> attendances) {
        // Group by user
        Map<Long, WeeklyLeaderAttendanceResult> leaderIdToAttendance = attendances.stream()
                .collect(Collectors.toMap(
                        WeeklyLeaderAttendanceRow::getLeaderId,
                        attendance -> {
                            WeeklyLeaderAttendanceResult weeklyLeaderAttendanceResult = WeeklyLeaderAttendanceResult.builder()
                                    .managerId(attendance.getManagerId())
                                    .managerName(attendance.getManagerName())
                                    .leaderId(attendance.getLeaderId())
                                    .leaderName(attendance.getLeaderName())
                                    .sex(attendance.getSex())
                                    .grade(attendance.getGrade())
                                    .phoneNumber(attendance.getPhoneNumber())
                                    .build();

                            weeklyLeaderAttendanceResult.getAttendanceItems().add(
                                    WeeklyLeaderAttendanceResult.WeeklyLeaderAttendanceItem.builder()
                                            .week(attendance.getWeek())
                                            .totalRegistered(attendance.getTotalRegistered())
                                            .totalAttendance(attendance.getTotalAttendance())
                                            .build()
                            );

                            return weeklyLeaderAttendanceResult;
                        },
                        (prev, curr) -> {
                            curr.getAttendanceItems().addAll(prev.getAttendanceItems());
                            return curr;
                        }
                ));

        // Sort inner attendanceItems by week ASC
        leaderIdToAttendance.values()
                .forEach(el -> el.getAttendanceItems().sort((a, b) -> a.getWeek().isBefore(b.getWeek()) ? -1 : 1));

        // Sort outer attendances by managerId ASC
        List<WeeklyLeaderAttendanceResult> result = new ArrayList<>(leaderIdToAttendance.values());
        result.sort(comparing(WeeklyLeaderAttendanceResult::getManagerId));

        return result;
    }

    public List<WeeklyManagerAttendanceResult> findAllManagerAttendanceByTerm(Long termId) {
        Term term = queryFactory
                .select(QTerm.term)
                .from(QTerm.term)
                .where(QTerm.term.id.eq(termId))
                .fetchOne();

        List<WeeklyManagerAttendanceRow> attendances = queryFactory
                .select(Projections.constructor(WeeklyManagerAttendanceRow.class,
                        weeklyManagerAttendance.managerId.as("managerId"),
                        weeklyManagerAttendance.managerName.as("managerName"),
                        weeklyAttendance.week.as("week"),
                        weeklyManagerAttendance.totalRegistered.as("totalRegistered"),
                        weeklyManagerAttendance.totalAttendance.as("totalAttendance")
                ))
                .from(weeklyManagerAttendance)
                .join(weeklyAttendance).on(
                        weeklyAttendance.id.eq(weeklyManagerAttendance.weeklyAttendanceId),
                        weeklyAttendance.week.week.between(term.getStartDate(), term.getEndDate())
                )
                .fetch();

        return groupByManager(attendances);
    }

    private List<WeeklyManagerAttendanceResult> groupByManager(List<WeeklyManagerAttendanceRow> attendances) {
        // Group by manager
        Map<Long, WeeklyManagerAttendanceResult> managerIdToAttendances = attendances.stream()
                .collect(Collectors.toMap(
                        WeeklyManagerAttendanceRow::getManagerId,
                        attendance -> {
                            WeeklyManagerAttendanceResult weeklyManagerAttendanceResult = new WeeklyManagerAttendanceResult(
                                    attendance.getManagerId(),
                                    attendance.getManagerName()
                            );

                            weeklyManagerAttendanceResult.getAttendanceItems().add(
                                    WeeklyManagerAttendanceResult.WeeklyManagerAttendanceItem.builder()
                                            .week(attendance.getWeek())
                                            .totalRegistered(attendance.getTotalRegistered())
                                            .totalAttendance(attendance.getTotalAttendance())
                                            .build()
                            );

                            return weeklyManagerAttendanceResult;
                        },
                        (prev, curr) -> {
                            curr.getAttendanceItems().addAll(prev.getAttendanceItems());
                            return curr;
                        }
                ));

        // Sort inner attendanceItems by week ASC
        managerIdToAttendances.values()
                .forEach(el -> el.getAttendanceItems().sort((a, b) -> a.getWeek().isBefore(b.getWeek()) ? -1 : 1));

        return new ArrayList<>(managerIdToAttendances.values());
    }

    public List<WeeklyGradeAttendanceResult> findAllGradeAttendanceByTerm(Long termId) {
        Term term = queryFactory
                .select(QTerm.term)
                .from(QTerm.term)
                .where(QTerm.term.id.eq(termId))
                .fetchOne();

        List<WeeklyGradeAttendanceRow> attendances = queryFactory
                .select(Projections.constructor(WeeklyGradeAttendanceRow.class,
                        weeklyGradeAttendance.grade.as("grade"),
                        weeklyAttendance.week.as("week"),
                        weeklyGradeAttendance.totalRegistered.as("totalRegistered"),
                        weeklyGradeAttendance.totalAttendance.as("totalAttendance")
                ))
                .from(weeklyGradeAttendance)
                .join(weeklyAttendance).on(
                        weeklyAttendance.id.eq(weeklyGradeAttendance.weeklyAttendanceId),
                        weeklyAttendance.week.week.between(term.getStartDate(), term.getEndDate())
                )
                .fetch();

        return groupByGrade(attendances);
    }

    private List<WeeklyGradeAttendanceResult> groupByGrade(List<WeeklyGradeAttendanceRow> attendances) {
        // Group by grade
        Map<Integer, WeeklyGradeAttendanceResult> gradeToAttendances = attendances.stream()
                .collect(Collectors.toMap(
                        WeeklyGradeAttendanceRow::getGrade,
                        attendance -> {
                            WeeklyGradeAttendanceResult weeklyGradeAttendanceResult = new WeeklyGradeAttendanceResult(attendance.getGrade());

                            weeklyGradeAttendanceResult.getAttendanceItems().add(
                                    WeeklyGradeAttendanceResult.WeeklyGradeAttendanceItem.builder()
                                            .week(attendance.getWeek())
                                            .totalRegistered(attendance.getTotalRegistered())
                                            .totalAttendance(attendance.getTotalAttendance())
                                            .build()
                            );

                            return weeklyGradeAttendanceResult;
                        },
                        (prev, curr) -> {
                            curr.getAttendanceItems().addAll(prev.getAttendanceItems());
                            return curr;
                        }
                ));

        // Sort inner attendanceItems by week ASC
        gradeToAttendances.values()
                .forEach(el -> el.getAttendanceItems().sort((a, b) -> a.getWeek().isBefore(b.getWeek()) ? -1 : 1));

        List<WeeklyGradeAttendanceResult> result = new ArrayList<>(gradeToAttendances.values());
        result.sort(comparingInt(WeeklyGradeAttendanceResult::getGrade));

        return result;
    }

    public TermPersonalAttendanceRateResult findAllAttendanceRateByTerm(Long termId) {
        Term term = queryFactory
                .select(QTerm.term)
                .from(QTerm.term)
                .where(QTerm.term.id.eq(termId))
                .fetchOne();

        Long termAttendanceId = queryFactory
                .select(termAttendance.id)
                .from(termAttendance)
                .where(termAttendance.termId.eq(termId))
                .fetchOne();

        List<TermPersonalAttendanceRateResult.TermPersonalAttendanceRateItem> termPersonalAttendances = queryFactory
                .select(Projections.constructor(TermPersonalAttendanceRateResult.TermPersonalAttendanceRateItem.class,
                        userEntity.name.as("userName"),
                        userEntity.sex.as("sex"),
                        userEntity.grade.as("grade"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        termPersonalAttendance.totalWeekPassed.as("totalWeekPassed"),
                        termPersonalAttendance.totalAttend.as("totalAttend"),
                        termPersonalAttendance.totalOnline.as("totalOnline"),
                        termPersonalAttendance.totalAbsent.as("totalAbsent")
                ))
                .from(termPersonalAttendance)
                .join(userEntity).on(
                        userEntity.id.eq(termPersonalAttendance.userId),
                        termPersonalAttendance.termAttendanceId.eq(termAttendanceId)
                )
                .fetch();

        termPersonalAttendances.sort((a, b) -> {
            double aRate = (a.getTotalAttend() + a.getTotalOnline()) / (double) a.getTotalWeekPassed();
            double bRate = (b.getTotalAttend() + b.getTotalOnline()) / (double) b.getTotalWeekPassed();

            return Double.compare(bRate, aRate);
        });

        return new TermPersonalAttendanceRateResult(
                term.getName(),
                term.getStartDate(),
                term.getEndDate(),
                termPersonalAttendances
        );
    }
}
