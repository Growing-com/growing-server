package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.query.model.AttendanceRegisterRate;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.sarangchurch.growing.v1.feat.attendance.domain.attendance.QAttendance.attendance;
import static org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.QNewFamilyAttendance.newFamilyAttendance;
import static org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.QStumpAttendance.stumpAttendance;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
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
public class AttendanceRegisterRateQueryRepository {
    private final JPAQueryFactory queryFactory;

    public AttendanceRegisterRate getByDate(LocalDate date) {
        Term activeTerm = queryFactory
                .select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        Long totalActive = queryFactory.select(user.count())
                .from(user)
                .where(user.isActive.isTrue())
                .fetchOne();

        // 그루터기 START
        Long pastorCount = queryFactory.select(pastor.count())
                .from(pastor)
                .where(pastor.termId.eq(activeTerm.getId()))
                .fetchOne();

        Long codyCount = queryFactory.select(cody.count())
                .from(cody)
                .where(cody.termId.eq(activeTerm.getId()))
                .fetchOne();

        Long totalStumpRegistered = queryFactory.select(stumpAttendance.count())
                .from(stumpAttendance)
                .where(stumpAttendance.date.eq(date))
                .fetchOne();
        // 그루터기 END

        // 순장 + 순원 START
        List<Long> attendancesUserIds = queryFactory.select(attendance.userId)
                .from(attendance)
                .where(attendance.date.eq(date))
                .fetch();

        List<Long> smallGroupLeaderUserIds = queryFactory.select(smallGroup.leaderUserId)
                .from(smallGroup)
                .where(smallGroup.termId.eq(activeTerm.getId()))
                .fetch();

        List<Long> smallGroupMemberUserIds = queryFactory.select(smallGroupMember.userId)
                .from(smallGroupMember)
                .where(smallGroupMember.termId.eq(activeTerm.getId()))
                .fetch();

        List<Long> newFamilyGroupLeaderUserIds = queryFactory.select(newFamilyGroup.leaderUserId)
                .from(newFamilyGroup)
                .where(newFamilyGroup.termId.eq(activeTerm.getId()))
                .fetch();

        List<Long> newFamilyGroupMemberUserIds = queryFactory.select(newFamilyGroupMember.userId)
                .from(newFamilyGroupMember)
                .where(newFamilyGroupMember.termId.eq(activeTerm.getId()))
                .fetch();

        // 순장 + 순원 END

        // 새가족 START
        Long totalNewFamilies = queryFactory.select(newFamily.count())
                .from(newFamily)
                .where(newFamily.status.ne(NewFamilyStatus.PROMOTED))
                .fetchOne();

        Long totalNewFamiliesRegistered = queryFactory.select(newFamilyAttendance.count())
                .from(newFamilyAttendance)
                .where(newFamilyAttendance.date.eq(date))
                .fetchOne();
        // 새가족 END

        return new AttendanceRegisterRate(
                // 전체
                totalActive,
                totalStumpRegistered + attendancesUserIds.size() + totalNewFamiliesRegistered,
                // 그루터기
                pastorCount + codyCount,
                totalStumpRegistered,
                // 순장
                (long) smallGroupLeaderUserIds.size(),
                attendancesUserIds.stream().filter(smallGroupLeaderUserIds::contains).count(),
                (long) newFamilyGroupLeaderUserIds.size(),
                attendancesUserIds.stream().filter(newFamilyGroupLeaderUserIds::contains).count(),
                // 순원
                (long) smallGroupMemberUserIds.size(),
                attendancesUserIds.stream().filter(smallGroupMemberUserIds::contains).count(),
                (long) newFamilyGroupMemberUserIds.size(),
                attendancesUserIds.stream().filter(newFamilyGroupMemberUserIds::contains).count(),
                // 새가족
                totalNewFamilies,
                totalNewFamiliesRegistered
        );
    }
}
