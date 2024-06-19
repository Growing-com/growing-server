package org.sarangchurch.growing.v2.feat.newfamily.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.QNewFamily.newFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v2.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class NewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamily> findAll() {
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(NewFamily.class,
                        user.id,
                        user.name,
                        user.phoneNumber,
                        user.birth,
                        user.gender,
                        user.grade,
                        newFamily.visitDate,
                        newFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        smallGroupLeaderUser.name
                ))
                .from(newFamily)
                .join(user).on(user.id.eq(newFamily.userId))
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(newFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                .leftJoin(smallGroup).on(smallGroup.id.eq(newFamily.smallGroupId))
                .leftJoin(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId))
                .leftJoin(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                .fetch();
    }
}
