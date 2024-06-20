package org.sarangchurch.growing.v2.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.NewFamilyGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyGroup> findByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(NewFamilyGroup.class,
                        newFamilyGroup.id,
                        user.name
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(
                        newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroup.termId.eq(termId))
                .join(user).on(user.id.eq(newFamilyGroupLeader.userId))
                .fetch();

    }
}
