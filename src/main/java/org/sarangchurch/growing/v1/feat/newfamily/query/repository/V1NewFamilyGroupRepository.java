package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamilyGroupListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class V1NewFamilyGroupRepository {
    private final JPAQueryFactory queryFactory;

    public List<V1NewFamilyGroupListItem> findActive() {
        Long activeTermId = queryFactory
                .select(term.id)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        return queryFactory
                .select(Projections.constructor(V1NewFamilyGroupListItem.class,
                        newFamilyGroup.id.as("newFamilyGroupId"),
                        user.name.as("newFamilyGroupLeaderName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(
                        newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroupLeader.termId.eq(activeTermId)
                )
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .fetch();
    }
}
