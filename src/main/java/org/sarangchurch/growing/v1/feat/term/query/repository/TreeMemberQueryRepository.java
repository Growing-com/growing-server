package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.TreeMemberListItem;
import org.sarangchurch.growing.v1.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class TreeMemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<TreeMemberListItem> findBySmallGroup(Long smallGroupId) {
        QUser leaderUser = new QUser("leaderUser");

        // 순장
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
                        user.name.as("smallGroupLeaderName")
                ))
                .from(smallGroupLeader)
                .join(user).on(user.id.eq(smallGroupLeader.userId), smallGroupLeader.id.eq(smallGroupLeaderId))
                .fetchOne();

        // 순원
        List<TreeMemberListItem> smallGroupMemberList = queryFactory.select(Projections.constructor(TreeMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        leaderUser.name.as("smallGroupLeaderName")
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
        return null;
    }
}
