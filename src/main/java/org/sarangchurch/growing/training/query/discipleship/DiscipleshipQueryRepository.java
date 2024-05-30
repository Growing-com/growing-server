package org.sarangchurch.growing.training.query.discipleship;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.training.domain.discipleship.QDiscipleship.discipleship;
import static org.sarangchurch.growing.training.domain.discipleship.QDiscipleshipMember.discipleshipMember;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class DiscipleshipQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Discipleship> findAll() {
        return queryFactory.select(Projections.constructor(Discipleship.class,
                        discipleship.id.as("id"),
                        discipleship.name.as("name"),
                        discipleship.startDate.as("startDate"),
                        discipleship.endDate.as("endDate"),
                        discipleship.etc.as("etc")
                ))
                .from(discipleship)
                .orderBy(discipleship.startDate.desc())
                .fetch();
    }

    public Discipleship findById(Long discipleshipId) {
        Discipleship findDiscipleShip = queryFactory.select(Projections.constructor(Discipleship.class,
                        discipleship.id.as("id"),
                        discipleship.name.as("name"),
                        discipleship.startDate.as("startDate"),
                        discipleship.endDate.as("endDate"),
                        discipleship.etc.as("etc")
                ))
                .from(discipleship)
                .where(discipleship.id.eq(discipleshipId))
                .fetchOne();

        if (findDiscipleShip == null) {
            throw new IllegalArgumentException("존재하지 않는 제자반입니다");
        }

        List<Discipleship.DiscipleshipMember> discipleshipMembers = queryFactory.select(Projections.constructor(Discipleship.DiscipleshipMember.class,
                        userEntity.id.as("userId"),
                        discipleshipMember.discipleShipId.as("discipleShipId"),
                        userEntity.grade.as("grade"),
                        userEntity.name.as("name"),
                        userEntity.sex.as("sex"),
                        userEntity.phoneNumber.as("phoneNumber")
                ))
                .from(discipleshipMember)
                .join(userEntity).on(userEntity.id.eq(discipleshipMember.userId))
                .where(discipleshipMember.discipleShipId.eq(discipleshipId))
                .fetch();

        findDiscipleShip.setMembers(discipleshipMembers);

        return findDiscipleShip;
    }
}
