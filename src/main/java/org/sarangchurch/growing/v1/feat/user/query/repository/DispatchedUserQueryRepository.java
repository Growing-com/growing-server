package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.query.model.DispatchedUserListItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.QDispatchedUser.dispatchedUser;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class DispatchedUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<DispatchedUserListItem> findAll() {
        return queryFactory
                .select(Projections.constructor(DispatchedUserListItem.class,
                        dispatchedUser.id.as("dispatchedUserId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.birth.as("birth"),
                        user.grade.as("grade"),
                        dispatchedUser.type.as("type"),
                        dispatchedUser.sendDate.as("sendDate"),
                        dispatchedUser.returnDate.as("returnDate")
                ))
                .from(dispatchedUser)
                .join(user).on(user.id.eq(dispatchedUser.userId))
                .orderBy(dispatchedUser.type.asc())
                .fetch();
    }
}
