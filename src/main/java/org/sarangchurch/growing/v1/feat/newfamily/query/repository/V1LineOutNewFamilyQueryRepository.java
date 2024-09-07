package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1LineOutNewFamilyListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.QLineoutNewFamily.lineoutNewFamily;
import static org.sarangchurch.growing.v1.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class V1LineOutNewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<V1LineOutNewFamilyListItem> findAll() {
        return queryFactory.select(Projections.constructor(V1LineOutNewFamilyListItem.class,
                        lineoutNewFamily.id.as("lineOutNewFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        lineoutNewFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        lineoutNewFamily.lineOutDate.as("lineOutDate")
                ))
                .from(lineoutNewFamily)
                .join(user).on(user.id.eq(lineoutNewFamily.userId))
                .orderBy(lineoutNewFamily.lineOutDate.desc())
                .fetch();
    }
}
