package org.sarangchurch.growing.v2.feat.newfamily.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.LinedoutNewFamily;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.QNewFamilyGroup.newFamilyGroup;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.QLineoutNewFamily.lineoutNewFamily;
import static org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.QNewFamilyGroupLeader.newFamilyGroupLeader;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class LinedoutNewFamilyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<LinedoutNewFamily> findAll() {
        QUser newFamilyGroupLeaderUser = new QUser("newFamilyGroupLeaderUser");

        return queryFactory.select(Projections.constructor(LinedoutNewFamily.class,
                        lineoutNewFamily.id,
                        user.name,
                        user.phoneNumber,
                        user.birth,
                        user.sex,
                        user.grade,
                        lineoutNewFamily.visitDate,
                        lineoutNewFamily.etc,
                        newFamilyGroupLeaderUser.name,
                        lineoutNewFamily.createdAt
                ))
                .from(lineoutNewFamily)
                .join(user).on(user.id.eq(lineoutNewFamily.userId))
                // 새가족반
                .leftJoin(newFamilyGroup).on(newFamilyGroup.id.eq(lineoutNewFamily.newFamilyGroupId))
                .leftJoin(newFamilyGroupLeader).on(newFamilyGroupLeader.id.eq(newFamilyGroup.newFamilyGroupLeaderId))
                .leftJoin(newFamilyGroupLeaderUser).on(newFamilyGroupLeaderUser.id.eq(newFamilyGroupLeader.userId))
                // 정렬
                .orderBy(lineoutNewFamily.createdAt.desc())
                .fetch();
    }
}
