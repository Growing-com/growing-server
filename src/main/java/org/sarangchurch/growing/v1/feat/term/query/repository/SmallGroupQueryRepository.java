package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupMemberListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.QSmallGroupMember.smallGroupMember;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class SmallGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<SmallGroupListItem> findByTermId(Long termId) {
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");
        QUser codyUser = new QUser("codyUser");

        List<SmallGroupListItem.SmallGroupListItemLeaders> smallGroupListItemLeaders = queryFactory
                .select(Projections.constructor(SmallGroupListItem.SmallGroupListItemLeaders.class,
                        codyUser.id.as("codyUserId"),
                        codyUser.name.as("codyName"),
                        smallGroup.id.as("smallGroupId"),
                        smallGroupLeaderUser.name.as("smallGroupLeaderName")
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(
                        smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId),
                        smallGroup.termId.eq(termId)
                )
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();

        Map<Long, List<SmallGroupListItem.SmallGroupListItemLeaders>> groupedByUserId = smallGroupListItemLeaders.stream()
                .collect(Collectors.groupingBy(SmallGroupListItem.SmallGroupListItemLeaders::getCodyUserId));

        return groupedByUserId.values().stream()
                .map(SmallGroupListItem::new)
                .collect(Collectors.toList());
    }

    public List<SmallGroupMemberListItem> findMembersBySmallGroupId(Long smallGroupId) {
        return queryFactory.select(Projections.constructor(SmallGroupMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(smallGroupMember)
                .join(user).on(user.id.eq(smallGroupMember.userId), smallGroupMember.smallGroupId.eq(smallGroupId))
                .fetch();
    }
}
