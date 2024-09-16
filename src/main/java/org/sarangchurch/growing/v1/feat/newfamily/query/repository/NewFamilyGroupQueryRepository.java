package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyGroupListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyGroupListItem> findByTermId(Long termId) {
        return queryFactory
                .select(Projections.constructor(NewFamilyGroupListItem.class,
                        newFamilyGroup.id.as("newFamilyGroupId"),
                        user.name.as("newFamilyGroupLeaderName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(
                        newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroupLeader.termId.eq(termId)
                )
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .fetch();
    }
}
