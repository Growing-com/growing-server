package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.query.model.GraduatedUserListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.user.domain.graduateduser.QGraduatedUser.graduatedUser;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class GraduatedUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<GraduatedUserListItem> findAll() {
        return queryFactory
                .select(Projections.constructor(GraduatedUserListItem.class,
                        graduatedUser.id.as("graduatedUserId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        user.grade.as("grade"),
                        graduatedUser.graduateDate.as("graduateDate")
                ))
                .from(graduatedUser)
                .join(user).on(user.id.eq(graduatedUser.userId))
                .orderBy(graduatedUser.graduateDate.desc())
                .fetch();
    }
}
