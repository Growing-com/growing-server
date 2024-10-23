package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.sarangchurch.growing.v1.feat.user.query.model.DispatchedUserListItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.QDispatchedUser.dispatchedUser;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class DispatchedUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<DispatchedUserListItem> findAll() {
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

        List<Tuple> smallGroupLeaderTuple = queryFactory
                .select(user.id, leaderUser.name)
                .from(smallGroupMember)
                .join(smallGroup).on(smallGroup.id.eq(smallGroupMember.smallGroupId), smallGroupMember.userId.in(userIds))
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroupLeader.userId))
                .fetch();

        List<Tuple> newFamilyGroupLeaderTuple = queryFactory
                .select(user.id, leaderUser.name)
                .from(newFamilyGroupMember)
                .join(newFamilyGroup).on(newFamilyGroup.id.eq(newFamilyGroupMember.newFamilyGroupId), newFamilyGroupMember.userId.in(userIds))
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroupLeader.userId))
                .fetch();

        dispatchedUsers.forEach(dispatchedUser -> {
            Long userId = dispatchedUser.getUserId();

            smallGroupLeaderTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });

            newFamilyGroupLeaderTuple.stream()
                    .filter(tp -> userId.equals(tp.get(0, Long.class)))
                    .findAny()
                    .ifPresent(tp -> {
                        dispatchedUser.setLeaderName(tp.get(1, String.class));
                    });
        });

        return dispatchedUsers;
    }
}
