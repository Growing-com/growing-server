package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.CodyListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.GroupListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.GroupType;
import org.sarangchurch.growing.v1.feat.term.query.model.TreeMemberListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class TreeMemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CodyListItem> findCodiesByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(CodyListItem.class,
                        cody.id.as("codyId"),
                        user.name.as("codyName")
                ))
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.termId.eq(termId))
                .fetch();
    }

    public List<TreeMemberListItem> findBySmallGroup(Long smallGroupId) {
        QUser leaderUser = new QUser("leaderUser");

        // 일반 순장
        Long smallGroupLeaderId = queryFactory.select(smallGroup.smallGroupLeaderId)
                .from(smallGroup)
                .where(smallGroup.id.eq(smallGroupId))
                .fetchOne();

        TreeMemberListItem smallGroupLeaderListItem = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName")
                ))
                .from(smallGroupLeader)
                .join(user).on(user.id.eq(smallGroupLeader.userId), smallGroupLeader.id.eq(smallGroupLeaderId))
                .fetchOne();

        // 일반 순원
        List<TreeMemberListItem> smallGroupMemberList = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("leaderName")
                ))
                .from(smallGroup)
                .join(smallGroupMember).on(smallGroupMember.smallGroupId.eq(smallGroup.id), smallGroup.id.eq(smallGroupId))
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroupLeader.userId))
                .orderBy(user.grade.desc())
                .fetch();

        smallGroupMemberList.add(0, smallGroupLeaderListItem);

        return smallGroupMemberList;
    }

    public List<TreeMemberListItem> findByCody(Long codyId) {
        QUser leaderUser = new QUser("leaderUser");

        // 일반 순장
        List<TreeMemberListItem> result1 = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName")
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId), smallGroup.codyId.eq(codyId))
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .fetch();

        // 일반 순원
        List<TreeMemberListItem> result2 = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("leaderName")
                ))
                .from(smallGroup)
                .join(smallGroupMember).on(smallGroupMember.smallGroupId.eq(smallGroup.id), smallGroup.codyId.eq(smallGroup.codyId))
                .join(user).on(user.id.eq(smallGroupMember.userId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(smallGroupLeader.userId))
                .fetch();

        // 새가족 순장
        List<TreeMemberListItem> result3 = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("leaderName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroup.codyId.eq(codyId))
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .fetch();

        // 새가족 순원
        List<TreeMemberListItem> result4 = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("leaderName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupMember).on(newFamilyGroupMember.newFamilyGroupId.eq(newFamilyGroup.id),
                        newFamilyGroup.codyId.eq(codyId))
                .join(user).on(user.id.eq(newFamilyGroupMember.userId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(leaderUser).on(leaderUser.id.eq(newFamilyGroupLeader.userId))
                .fetch();

        List<TreeMemberListItem> result = new ArrayList<>();

        result.addAll(result1);
        result.addAll(result2);
        result.addAll(result3);
        result.addAll(result4);

        result.sort(Comparator.comparing(TreeMemberListItem::getLeaderName));

        return result;
    }

    public List<GroupListItem> findGroupListByCody(Long codyId) {
        List<GroupListItem> smallGroups = queryFactory.select(Projections.constructor(GroupListItem.class,
                        smallGroup.id.as("groupId"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(GroupType.SMALL_GROUP).as("groupType")
                ))
                .from(smallGroup)
                .join(cody).on(cody.id.eq(smallGroup.codyId), cody.id.eq(codyId))
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .fetch();

        List<GroupListItem> newFamilyGroups = queryFactory.select(Projections.constructor(GroupListItem.class,
                        newFamilyGroup.id.as("groupId"),
                        user.name.as("leaderName"),
                        Expressions.asEnum(GroupType.NEW_FAMILY_GROUP).as("groupType")
                ))
                .from(newFamilyGroup)
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId), cody.id.eq(codyId))
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .fetch();

        List<GroupListItem> result = new ArrayList<>();

        result.addAll(smallGroups);
        result.addAll(newFamilyGroups);

        return result;
    }
}
