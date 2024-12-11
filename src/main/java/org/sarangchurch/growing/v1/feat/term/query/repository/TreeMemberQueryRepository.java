package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.core.interfaces.common.types.GroupType;
import org.sarangchurch.growing.v1.feat.term.domain.cody.QCody;
import org.sarangchurch.growing.v1.feat.term.query.model.CodyListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.GroupListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.TreeMemberListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class TreeMemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CodyListItem> findCodiesByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(CodyListItem.class,
                        cody.id.as("codyId"),
                        user.name.as("codyName"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.termId.eq(termId))
                .fetch();
    }

    public List<TreeMemberListItem> findByCody(Long codyId) {
        QUser leaderUser = new QUser("leaderUser");

        // 코디
        TreeMemberListItem cody = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(Duty.CODY).as("duty")
                ))
                .from(QCody.cody)
                .join(user).on(user.id.eq(QCody.cody.userId), QCody.cody.id.eq(codyId))
                .fetchOne();

        // 일반 순장
        List<TreeMemberListItem> smallGroupLeaders = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(Duty.SMALL_GROUP_LEADER).as("duty")
                ))
                .from(smallGroup)
                .join(user).on(user.id.eq(smallGroup.leaderUserId), smallGroup.codyId.eq(codyId))
                .fetch();

        // 일반 순원
        List<TreeMemberListItem> smallGroupMembers = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("leaderName"),
                        Expressions.asEnum(Duty.SMALL_GROUP_MEMBER).as("duty")
                ))
                .from(smallGroup)
                .join(smallGroupMember).on(smallGroupMember.smallGroupId.eq(smallGroup.id), smallGroup.codyId.eq(codyId))
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroup.leaderUserId))
                .fetch();

        // 새가족 순장
        List<TreeMemberListItem> newFamilyGroupLeaders = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(Duty.NEW_FAMILY_GROUP_LEADER).as("duty")
                ))
                .from(newFamilyGroup)
                .join(user).on(user.id.eq(newFamilyGroup.leaderUserId), newFamilyGroup.codyId.eq(codyId))
                .fetch();

        // 새가족 순원
        List<TreeMemberListItem> newFamilyGroupMembers = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("leaderName"),
                        Expressions.asEnum(Duty.NEW_FAMILY_GROUP_MEMBER).as("duty")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupMember).on(newFamilyGroupMember.newFamilyGroupId.eq(newFamilyGroup.id),
                        newFamilyGroup.codyId.eq(codyId))
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroup.leaderUserId))
                .fetch();

        List<TreeMemberListItem> result = new ArrayList<>();

        result.addAll(smallGroupLeaders);
        result.addAll(smallGroupMembers);
        result.addAll(newFamilyGroupLeaders);
        result.addAll(newFamilyGroupMembers);

        result.sort(Comparator.comparing(TreeMemberListItem::getLeaderName));

        if (cody != null) {
            result.add(0, cody);
        }

        return result;
    }

    public List<GroupListItem> findGroupListByCody(Long codyId) {
        List<GroupListItem> smallGroups = queryFactory.select(Projections.constructor(GroupListItem.class,
                        smallGroup.id.as("groupId"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(GroupType.SMALL_GROUP).as("groupType"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(smallGroup)
                .join(cody).on(cody.id.eq(smallGroup.codyId), cody.id.eq(codyId))
                .join(user).on(user.id.eq(smallGroup.leaderUserId))
                .fetch();

        List<GroupListItem> newFamilyGroups = queryFactory.select(Projections.constructor(GroupListItem.class,
                        newFamilyGroup.id.as("groupId"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(GroupType.NEW_FAMILY_GROUP).as("groupType"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(newFamilyGroup)
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId), cody.id.eq(codyId))
                .join(user).on(user.id.eq(newFamilyGroup.leaderUserId))
                .fetch();

        List<GroupListItem> result = new ArrayList<>();

        result.addAll(smallGroups);
        result.addAll(newFamilyGroups);

        return result;
    }
}
