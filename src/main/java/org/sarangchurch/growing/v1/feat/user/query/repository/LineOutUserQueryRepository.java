package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.query.model.LineOutUserListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.QLineOutUser.lineOutUser;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class LineOutUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<LineOutUserListItem> findAll() {
        return queryFactory
                .select(Projections.constructor(LineOutUserListItem.class,
                        lineOutUser.id.as("lineOutUserId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        user.grade.as("grade"),
                        lineOutUser.lineOutDate.as("lineOutDate"),
                        lineOutUser.reason.as("reason")
                ))
                .from(lineOutUser)
                .join(user).on(user.id.eq(lineOutUser.userId))
                .orderBy(lineOutUser.lineOutDate.desc())
                .fetch();
    }
}
