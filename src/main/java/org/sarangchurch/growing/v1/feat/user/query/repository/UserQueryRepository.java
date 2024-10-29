package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.Duty;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.sarangchurch.growing.v1.feat.user.query.model.UserListItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
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

        // CODY
        List<UserListItem> codies = queryFactory.select(Projections.constructor(UserListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
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
                        Expressions.asEnum(Duty.NEW_FAMILY_GROUP_MEMBER).as("duty"),
                        newFamilyGroupLeaderUser.name.as("leaderName")
                ))
                .from(newFamilyGroupMember)
                .join(newFamilyGroup).on(newFamilyGroup.id.eq(newFamilyGroupMember.newFamilyGroupId), newFamilyGroup.termId.eq(activeTerm.getId()))
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeader.userId.eq(newFamilyGroupLeaderUser.id))
                .fetch();


        // AGGREGATION
        List<UserListItem> result = new ArrayList<>();

        result.addAll(codies);
        result.addAll(smallGroupLeaders);
        result.addAll(newFamilyGroupLeaders);
        result.addAll(smallGroupMembers);
        result.addAll(newFamilyGroupMembers);

        return result;
    }
}
