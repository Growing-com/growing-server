package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.Duty;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.sarangchurch.growing.v1.feat.user.query.model.DispatchedUserListItem;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.QDispatchedUser.dispatchedUser;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class DispatchedUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<DispatchedUserListItem> findAll() {
        Term activeTerm = queryFactory
                .select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        QUser leaderUser = new QUser("leaderUser");

        List<DispatchedUserListItem> dispatchedUsers = queryFactory.select(Projections.constructor(DispatchedUserListItem.class,
                        dispatchedUser.id.as("dispatchedUserId"),
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        user.grade.as("grade"),
                        dispatchedUser.type.as("type"),
                        dispatchedUser.sendDate.as("sendDate"),
                        dispatchedUser.returnDate.as("returnDate")
                ))
                .from(dispatchedUser)
                .join(user).on(user.id.eq(dispatchedUser.userId))
                .orderBy(dispatchedUser.type.asc())
                .fetch();

        List<Long> userIds = dispatchedUsers.stream()
                .map(DispatchedUserListItem::getUserId)
                .collect(Collectors.toList());

        // 코디 -> 담당 교역자
        List<Tuple> codyTuple = queryFactory.select(user.id, leaderUser.name)
                .from(cody)
                .join(user).on(
                        cody.userId.eq(user.id),
                        user.id.in(userIds),
                        cody.termId.eq(activeTerm.getId())
                )
                .join(pastor).on(pastor.termId.eq(activeTerm.getId()), pastor.isSenior.isTrue())
                .join(leaderUser).on(leaderUser.id.eq(pastor.userId))
                .fetch();

        // 일반 순장 -> 코디
        List<Tuple> smallGroupLeaderTuple = queryFactory.select(user.id, leaderUser.name)
                .from(smallGroupLeader)
                .join(smallGroup).on(
                        smallGroup.smallGroupLeaderId.eq(smallGroupLeader.id),
                        smallGroupLeader.userId.in(userIds),
                        smallGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .join(cody).on(smallGroup.codyId.eq(cody.id))
                .join(leaderUser).on(leaderUser.id.eq(cody.userId))
                .fetch();

        // 일반 순원 -> 일반 순장
        List<Tuple> smallGroupMemberTuple = queryFactory.select(user.id, leaderUser.name)
                .from(smallGroupMember)
                .join(smallGroup).on(
                        smallGroup.id.eq(smallGroupMember.smallGroupId),
                        smallGroupMember.userId.in(userIds),
                        smallGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroupLeader.userId))
                .fetch();

        // 새가족 순장 -> 코디
        List<Tuple> newFamilyGroupLeaderTuple = queryFactory.select(user.id, leaderUser.name)
                .from(newFamilyGroupLeader)
                .join(newFamilyGroup).on(
                        newFamilyGroup.newFamilyGroupLeaderId.eq(newFamilyGroupLeader.id),
                        newFamilyGroupLeader.userId.in(userIds),
                        newFamilyGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .join(cody).on(newFamilyGroup.codyId.eq(cody.id))
                .join(leaderUser).on(leaderUser.id.eq(cody.userId))
                .fetch();

        // 새가족 순원 -> 새가족 순장
        List<Tuple> newFamilyGroupMemberTuple = queryFactory.select(user.id, leaderUser.name)
                .from(newFamilyGroupMember)
                .join(newFamilyGroup).on(
                        newFamilyGroup.id.eq(newFamilyGroupMember.newFamilyGroupId),
                        newFamilyGroupMember.userId.in(userIds),
                        newFamilyGroup.termId.eq(activeTerm.getId())
                )
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroupLeader.userId))
                .fetch();

        dispatchedUsers.forEach(dispatchedUser -> {
            Long userId = dispatchedUser.getUserId();

            codyTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setDuty(Duty.CODY);
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });

            smallGroupLeaderTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setDuty(Duty.SMALL_GROUP_LEADER);
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });

            smallGroupMemberTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setDuty(Duty.SMALL_GROUP_MEMBER);
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });

            newFamilyGroupLeaderTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setDuty(Duty.NEW_FAMILY_GROUP_LEADER);
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });

            newFamilyGroupMemberTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setDuty(Duty.NEW_FAMILY_GROUP_MEMBER);
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });
        });

        dispatchedUsers.sort(Comparator.comparing(DispatchedUserListItem::getSendDate).reversed());

        return dispatchedUsers;
    }
}
