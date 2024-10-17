package org.sarangchurch.growing.v1.feat.user.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.query.model.UserAccount;
import org.springframework.stereotype.Repository;

import static org.sarangchurch.growing.v1.feat.user.domain.account.QAccount.account;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserAccountQueryRepository {
    private final JPAQueryFactory queryFactory;

    public UserAccount findByUserId(Long userId) {
        return queryFactory.select(Projections.constructor(UserAccount.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        account.role.as("role")
                ))
                .from(account)
                .join(user).on(user.id.eq(account.userId), user.id.eq(userId))
                .fetchOne();
    }
}
