package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.query.model.NewFamilyGroupLeaderListItem;
import org.sarangchurch.growing.v1.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;

@Repository
@RequiredArgsConstructor
public class NewFamilyLeaderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<NewFamilyGroupLeaderListItem> findByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        return queryFactory.select(Projections.constructor(NewFamilyGroupLeaderListItem.class,
                        newFamilyGroupLeaderUser.id.as("userId"),
                        newFamilyGroupLeaderUser.name.as("name"),
                        newFamilyGroupLeaderUser.sex.as("sex"),
                        newFamilyGroupLeaderUser.grade.as("grade"),
                        newFamilyGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        newFamilyGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId),
                        newFamilyGroup.termId.eq(termId))
                .join(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }
}
