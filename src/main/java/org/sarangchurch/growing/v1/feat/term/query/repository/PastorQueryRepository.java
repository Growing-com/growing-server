package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.PastorListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PastorQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<PastorListItem> findByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(PastorListItem.class,
                        pastor.id.as("pastorId"),
                        user.name.as("pastorName"),
                        pastor.isSenior.as("isSenior")
                ))
                .from(pastor)
                .join(user).on(user.id.eq(pastor.userId), pastor.termId.eq(termId))
                .orderBy(pastor.isSenior.desc())
                .fetch();
    }
}
