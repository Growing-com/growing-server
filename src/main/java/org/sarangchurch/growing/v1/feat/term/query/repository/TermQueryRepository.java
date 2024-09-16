package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.TermListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.term.domain.term.QTerm.term;

@Repository("V1TermQueryRepository")
@RequiredArgsConstructor
public class TermQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<TermListItem> findAll() {
        return queryFactory.select(Projections.constructor(TermListItem.class,
                        term.id.as("termId"),
                        term.name.as("name"),
                        term.startDate.as("startDate"),
                        term.endDate.as("endDate"),
                        term.isActive.as("isActive")
                ))
                .from(term)
                .orderBy(term.startDate.desc())
                .fetch();
    }

    public TermListItem findActive() {
        return queryFactory.select(Projections.constructor(TermListItem.class,
                        term.id.as("termId"),
                        term.name.as("name"),
                        term.startDate.as("startDate"),
                        term.endDate.as("endDate"),
                        term.isActive.as("isActive")
                ))
                .from(term)
                .where(term.isActive.isTrue())
                .fetchOne();
    }
}
