package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.query.model.StumpAttendanceListItem;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.QStumpAttendance.stumpAttendance;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class StumpAttendanceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<StumpAttendanceListItem> findByDate(LocalDate date) {
        Term activeTerm = queryFactory.select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        List<StumpAttendanceListItem.StumpAttendanceListItemAttendItem> result = queryFactory.select(Projections.constructor(StumpAttendanceListItem.StumpAttendanceListItemAttendItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        Expressions.asEnum(AttendanceStatus.NONE).as("status"),
                        Expressions.asDate(date).as("date"),
                        Expressions.asString("").as("reason")
                ))
                .from(cody)
                .join(user).on(
                        cody.userId.eq(user.id),
                        cody.termId.eq(activeTerm.getId())
                )
                .fetch();

        List<StumpAttendanceListItem.StumpAttendanceListItemAttendItem> existingAttendItems = queryFactory.select(Projections.constructor(StumpAttendanceListItem.StumpAttendanceListItemAttendItem.class,
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
                        stumpAttendance.date.eq(date)
                )
                .fetch();

        return result.stream()
                .map(it -> {
                    Optional<StumpAttendanceListItem.StumpAttendanceListItemAttendItem> optionalExistingItem = existingAttendItems.stream()
                            .filter(attendItem -> it.getUserId().equals(attendItem.getUserId()))
                            .findFirst();

                    if (optionalExistingItem.isPresent()) {
                        StumpAttendanceListItem.StumpAttendanceListItemAttendItem attendItem = optionalExistingItem.get();

                        return new StumpAttendanceListItem(
                                attendItem.getUserId(),
                                attendItem.getName(),
                                attendItem.getSex(),
                                attendItem.getGrade(),
                                List.of(attendItem)
                        );
                    } else {
                        return new StumpAttendanceListItem(
                                it.getUserId(),
                                it.getName(),
                                it.getSex(),
                                it.getGrade(),
                                List.of(it)
                        );
                    }
                })
                .sorted(Comparator.comparing(StumpAttendanceListItem::getName))
                .collect(Collectors.toList());
    }
}
