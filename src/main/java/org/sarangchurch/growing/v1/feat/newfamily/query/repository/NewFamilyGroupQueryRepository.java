package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyGroupListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyGroupMemberListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.QNewFamilyGroupMember.newFamilyGroupMember;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyGroupListItem> findByTermId(Long termId) {
        QUser codyUser = new QUser("codyUser");

        return queryFactory
                .select(Projections.constructor(NewFamilyGroupListItem.class,
                        newFamilyGroup.id.as("newFamilyGroupId"),
                        user.name.as("newFamilyGroupLeaderName"),
                        codyUser.name.as("codyName"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(
                        newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroupLeader.termId.eq(termId)
                )
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .fetch();
    }

    public List<NewFamilyGroupMemberListItem> findMembersByNewFamilyGroupId(Long newFamilyGroupId) {
        return queryFactory.select(Projections.constructor(NewFamilyGroupMemberListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade")
                ))
                .from(newFamilyGroupMember)
                .join(user).on(user.id.eq(newFamilyGroupMember.userId), newFamilyGroupMember.newFamilyGroupId.eq(newFamilyGroupId))
                .fetch();
    }
}
