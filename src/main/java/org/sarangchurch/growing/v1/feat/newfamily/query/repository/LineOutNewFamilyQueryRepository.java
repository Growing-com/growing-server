package org.sarangchurch.growing.v1.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.LineOutNewFamilyListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.QLineOutNewFamily.lineOutNewFamily;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class LineOutNewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<LineOutNewFamilyListItem> findAll() {
        return queryFactory.select(Projections.constructor(LineOutNewFamilyListItem.class,
                        lineOutNewFamily.id.as("lineOutNewFamilyId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        lineOutNewFamily.visitDate.as("visitDate"),
                        user.grade.as("grade"),
                        lineOutNewFamily.lineOutDate.as("lineOutDate")
                ))
                .from(lineOutNewFamily)
                .join(user).on(user.id.eq(lineOutNewFamily.userId))
                .orderBy(lineOutNewFamily.lineOutDate.desc())
                .fetch();
    }
}
