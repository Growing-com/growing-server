package org.sarangchurch.growing.v1.feat.term.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.term.query.model.LeaderListItem;
import org.sarangchurch.growing.v1.feat.user.domain.user.QUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v1.feat.term.domain.cody.QCody.cody;
import static org.sarangchurch.growing.v1.feat.term.domain.pastor.QPastor.pastor;
import static org.sarangchurch.growing.v1.feat.term.domain.smallgroup.QSmallGroup.smallGroup;
import static org.sarangchurch.growing.v1.feat.user.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class LeaderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<LeaderListItem> findCodiesByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("codyName")
                ))
                .from(cody)
                .join(user).on(user.id.eq(cody.userId), cody.termId.eq(termId))
                .fetch();
    }

    public List<LeaderListItem> findNewFamilyLeadersByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        newFamilyGroupLeaderUser.id.as("userId"),
                        newFamilyGroupLeaderUser.name.as("name"),
                        newFamilyGroupLeaderUser.sex.as("sex"),
                        newFamilyGroupLeaderUser.grade.as("grade"),
                        newFamilyGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        newFamilyGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(newFamilyGroup)
                .join(newFamilyGroupLeaderUser).on(
                        newFamilyGroupLeaderUser.id.eq(newFamilyGroup.leaderUserId),
                        newFamilyGroup.termId.eq(termId)
                )
                .join(cody).on(cody.id.eq(newFamilyGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }

    public List<LeaderListItem> findSmallGroupLeadersByTerm(Long termId) {
        QUser codyUser = new QUser("codyUser");
        QUser smallGroupLeaderUser = new QUser("smallGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        smallGroupLeaderUser.id.as("userId"),
                        smallGroupLeaderUser.name.as("name"),
                        smallGroupLeaderUser.sex.as("sex"),
                        smallGroupLeaderUser.grade.as("grade"),
                        smallGroupLeaderUser.phoneNumber.as("phoneNumber"),
                        smallGroupLeaderUser.birth.as("birth"),
                        codyUser.name.as("codyName")
                ))
                .from(smallGroup)
                .join(smallGroupLeaderUser).on(smallGroupLeaderUser.id.eq(smallGroup.leaderUserId), smallGroup.termId.eq(termId))
                .join(cody).on(cody.id.eq(smallGroup.codyId))
                .join(codyUser).on(codyUser.id.eq(cody.userId))
                .orderBy(codyUser.name.asc())
                .fetch();
    }

    public List<LeaderListItem> findAllByTerm(Long termId) {
        // 교역자
        List<LeaderListItem> pastors = findPastorsByTerm(termId);

        pastors.forEach(it -> it.setDuty(Duty.PASTOR));

        // 코디
        List<LeaderListItem> codies = findCodiesByTerm(termId);

        codies.forEach(it -> it.setDuty(Duty.CODY));

        // 일반 순장
        List<LeaderListItem> smallGroupLeaders = findSmallGroupLeadersByTerm(termId);

        smallGroupLeaders.forEach(it -> it.setDuty(Duty.SMALL_GROUP_LEADER));

        // 새가족 순장
        List<LeaderListItem> newFamilyLeaders = findNewFamilyLeadersByTerm(termId);

        newFamilyLeaders.forEach(it -> it.setDuty(Duty.NEW_FAMILY_GROUP_LEADER));

        // 취합 및 정렬
        List<LeaderListItem> result = new ArrayList<>();

        result.addAll(pastors);
        result.addAll(codies);
        result.addAll(smallGroupLeaders);
        result.addAll(newFamilyLeaders);

        return result;
    }

    private List<LeaderListItem> findPastorsByTerm(Long termId) {
        return queryFactory.select(Projections.constructor(LeaderListItem.class,
                        user.id.as("userId"),
                        user.name.as("name"),
                        user.sex.as("sex"),
                        user.grade.as("grade"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        user.name.as("codyName")
                ))
                .from(pastor)
                .join(user).on(user.id.eq(pastor.userId), pastor.termId.eq(termId))
                .fetch();
    }
}
