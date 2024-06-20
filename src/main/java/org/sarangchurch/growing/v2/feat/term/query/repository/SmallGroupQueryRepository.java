package org.sarangchurch.growing.v2.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.term.query.model.SmallGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class SmallGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<SmallGroup> findAllByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(SmallGroup.class,
                        smallGroup.id,
                        user.name
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(
                        smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId),
                        smallGroup.termId.eq(termId))
                .join(user).on(user.id.eq(smallGroupLeader.userId))
                .fetch();
    }
}
