package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.sarangchurch.growing.v1.feat.user.query.model.User;
import org.sarangchurch.growing.v1.feat.user.query.model.UserListItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.QNewFamilyPromoteLog.newFamilyPromoteLog;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<UserListItem> findAll() {
        QUser codyUser = new QUser("codyUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        Term activeTerm = queryFactory
                .select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        UserListItem seniorPastor = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.PASTOR).as("duty"),
                        user.name.as("leaderName")
                ))
                .from(pastor)
                .join(user).on(
                        user.id.eq(pastor.userId),
                        pastor.termId.eq(activeTerm.getId()),
                        pastor.isSenior.isTrue()
                )
                .fetchOne();

        assert seniorPastor != null;

        // PASTOR
        List<UserListItem> juniorPastors = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.PASTOR).as("duty"),
                        Expressions.asString(seniorPastor.getName()).as("leaderName")
                ))
                .from(pastor)
                .join(user).on(
                        user.id.eq(pastor.userId),
                        pastor.termId.eq(activeTerm.getId()),
                        pastor.isSenior.isFalse()
                )
                .fetch();

        // CODY
        List<UserListItem> codies = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.CODY).as("duty"),
                        Expressions.asString(seniorPastor.getName()).as("leaderName")
                ))
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.termId.eq(activeTerm.getId()))
                .fetch();

        // SMALL_GROUP_LEADER
        List<UserListItem> smallGroupLeaders = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.SMALL_GROUP_LEADER).as("duty"),
                        codyUser.name.as("leaderName")
                ))
                .from(smallGroupLeader)
                .join(smallGroup).on(smallGroup.smallGroupLeaderId.eq(smallGroupLeader.id), smallGroup.termId.eq(activeTerm.getId()))
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .join(cody).on(smallGroup.codyId.eq(cody.id))
                .join(codyUser).on(cody.userId.eq(codyUser.id))
                .fetch();

        // NEW_FAMILY_GROUP_LEADER
        List<UserListItem> newFamilyGroupLeaders = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.NEW_FAMILY_GROUP_LEADER).as("duty"),
                        codyUser.name.as("leaderName")
                ))
                .from(newFamilyGroupLeader)
                .join(newFamilyGroup).on(newFamilyGroup.newFamilyGroupLeaderId.eq(newFamilyGroupLeader.id), newFamilyGroup.termId.eq(activeTerm.getId()))
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .join(cody).on(newFamilyGroup.codyId.eq(cody.id))
                .join(codyUser).on(cody.userId.eq(codyUser.id))
                .fetch();

        // SMALL_GROUP_MEMBER
        List<UserListItem> smallGroupMembers = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.SMALL_GROUP_MEMBER).as("duty"),
                        smallGroupLeaderUser.name.as("leaderName")
                ))
                .from(smallGroupMember)
                .join(smallGroup).on(smallGroup.id.eq(smallGroupMember.smallGroupId), smallGroup.termId.eq(activeTerm.getId()))
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(smallGroupLeaderUser).on(smallGroupLeader.userId.eq(smallGroupLeaderUser.id))
                .fetch();

        // NEW_FAMILY_GROUP_MEMBER
        List<UserListItem> newFamilyGroupMembers = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.NEW_FAMILY_GROUP_MEMBER).as("duty"),
                        newFamilyGroupLeaderUser.name.as("leaderName")
                ))
                .from(newFamilyGroupMember)
                .join(newFamilyGroup).on(newFamilyGroup.id.eq(newFamilyGroupMember.newFamilyGroupId), newFamilyGroup.termId.eq(activeTerm.getId()))
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeader.userId.eq(newFamilyGroupLeaderUser.id))
                .fetch();

        // NEW_FAMILY
        List<UserListItem> newFamilies = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.NEW_FAMILY).as("duty"),
                        newFamilyGroupLeaderUser.name.as("leaderName")
                ))
                .from(newFamily)
                .leftJoin(newFamilyPromoteLog).on(newFamilyPromoteLog.id.eq(newFamily.newFamilyPromoteLogId))
                .join(user).on(user.id.eq(newFamily.userId))
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeader.userId.eq(newFamilyGroupLeaderUser.id))
                .where(newFamily.newFamilyPromoteLogId.isNull().or(newFamilyPromoteLog.promoteDate.isNull()))
                .fetch();

        // NOT_PLACED
        List<Long> placedUserIds = new ArrayList<>();

        placedUserIds.add(seniorPastor.getUserId());
        placedUserIds.addAll(juniorPastors.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(codies.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(smallGroupLeaders.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(newFamilyGroupLeaders.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(smallGroupMembers.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(newFamilyGroupMembers.stream().map(UserListItem::getUserId).collect(Collectors.toList()));
        placedUserIds.addAll(newFamilies.stream().map(UserListItem::getUserId).collect(Collectors.toList()));

        List<UserListItem> notPlacedUsers = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc"),
                        Expressions.asEnum(Duty.NOT_PLACED).as("duty"),
                        Expressions.asString("").as("leaderName")
                ))
                .from(user)
                .where(user.isActive.isTrue(), user.id.notIn(placedUserIds))
                .fetch();

        // AGGREGATION
        List<UserListItem> result = new ArrayList<>();

        result.addAll(codies);
        result.addAll(smallGroupLeaders);
        result.addAll(newFamilyGroupLeaders);
        result.addAll(smallGroupMembers);
        result.addAll(newFamilyGroupMembers);
        result.addAll(notPlacedUsers);

        return result;
    }

    public User findById(Long userId) {
        Term activeTerm = queryFactory
                .select(term)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        assert activeTerm != null;

        User result = queryFactory.select(Projections.constructor(User.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.etc.as("etc")
                ))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        Long smallGroupId = queryFactory.select(smallGroupMember.smallGroupId)
                .from(smallGroupMember)
                .where(smallGroupMember.userId.eq(userId),
                        smallGroupMember.termId.eq(activeTerm.getId()))
                .fetchOne();

        if (result != null && smallGroupId != null) {
            result.setSmallGroupId(smallGroupId);
        }

        return result;
    }
}
