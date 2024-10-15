package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupLeaderListItem;
import org.sarangchurch.growing.v1.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.QSmallGroupLeader.smallGroupLeader;

@Repository
@RequiredArgsConstructor
public class SmallGroupLeaderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<SmallGroupLeaderListItem> findByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(SmallGroupLeaderListItem.class,
                        smallGroupLeaderUser.id.as("userId"),
                        smallGroupLeaderUser.name.as("name"),
                        smallGroupLeaderUser.sex.as("sex"),
                        smallGroupLeaderUser.grade.as("grade"),
                        smallGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        smallGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(smallGroup)
                .join(smallGroupLeader).on(smallGroupLeader.id.eq(smallGroup.smallGroupLeaderId), smallGroup.termId.eq(termId))
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroupLeader.userId))
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }
}
