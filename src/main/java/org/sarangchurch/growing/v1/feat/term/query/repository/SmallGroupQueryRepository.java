package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class SmallGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<SmallGroupListItem> findAll() {
        Long activeTermId = queryFactory
                .select(term.id)
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();

        return queryFactory
                .select(Projections.constructor(SmallGroupListItem.class,
                        smallGroup.id.as("smallGroupId"),
                        user.name.as("smallGroupLeaderName")
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(
                        smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId),
                        smallGroup.termId.eq(activeTermId)
                )
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .fetch();
    }
}
