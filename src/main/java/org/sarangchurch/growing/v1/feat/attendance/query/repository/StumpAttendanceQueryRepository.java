package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.query.model.StumpAttendanceListItem;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
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

        List<User> codyUsers = queryFactory.select(user)
                .from(cody)
                .join(user).on(
                        cody.userId.eq(user.id),
                        cody.termId.eq(activeTerm.getId())
                )
                .fetch();

        List<StumpAttendanceListItem.StumpAttendanceListItemAttendItem> attendItems = queryFactory.select(Projections.constructor(StumpAttendanceListItem.StumpAttendanceListItemAttendItem.class,
                        user.id.as("userId"),
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

        return codyUsers.stream()
                .map(codyUser -> {
                    Optional<StumpAttendanceListItem.StumpAttendanceListItemAttendItem> op = attendItems.stream()
                            .filter(attendItem -> codyUser.getId().equals(attendItem.getUserId()))
                            .findFirst();

                    if (op.isPresent()) {
                        StumpAttendanceListItem.StumpAttendanceListItemAttendItem item = op.get();

                        return new StumpAttendanceListItem(
                                codyUser.getId(),
                                codyUser.getName(),
                                codyUser.getSex(),
                                codyUser.getGrade(),
                                List.of(item)
                        );
                    } else {
                        StumpAttendanceListItem.StumpAttendanceListItemAttendItem defaultItem = new StumpAttendanceListItem.StumpAttendanceListItemAttendItem(
                                codyUser.getId(),
                                AttendanceStatus.NONE,
                                date,
                                ""
                        );

                        return new StumpAttendanceListItem(
                                codyUser.getId(),
                                codyUser.getName(),
                                codyUser.getSex(),
                                codyUser.getGrade(),
                                List.of(defaultItem)
                        );
                    }
                })
                .sorted(Comparator.comparing(StumpAttendanceListItem::getName))
                .collect(Collectors.toList());
    }
}
