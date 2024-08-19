package org.sarangchurch.growing.user.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.v2.feat.auth.domain.QAccount;
import org.sarangchurch.growing.v2.feat.user.domain.QUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.term.domain.team.QTeam.team;
import static org.sarangchurch.growing.term.domain.team.QTeamMember.teamMember;
import static org.sarangchurch.growing.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.training.domain.discipleship.QDiscipleship.discipleship;
import static org.sarangchurch.growing.training.domain.discipleship.QDiscipleshipMember.discipleshipMember;
import static org.sarangchurch.growing.training.domain.training.QTraining.training;
import static org.sarangchurch.growing.training.domain.training.QTrainingMember.trainingMember;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;
import static org.sarangchurch.growing.v2.feat.auth.domain.QAccount.*;
import static org.sarangchurch.growing.v2.feat.user.domain.QUser.*;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Account> findAllAccounts() {
        return queryFactory
                .select(Projections.constructor(Account.class,
                        userEntity.id.as("id"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        teamMember.duty.as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.isActive.as("isActive"),
                        userEntity.visitDate.as("visitDate"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(term)
                .join(team).on(team.termId.eq(term.id), term.isActive.isTrue())
                .join(teamMember).on(teamMember.teamId.eq(team.id))
                .join(userEntity).on(userEntity.id.eq(teamMember.memberId))
                .groupBy(userEntity.id)
                .orderBy(userEntity.name.asc())
                .fetch();
    }

    public List<Account> findAllInactiveAccounts() {
        return queryFactory
                .select(Projections.constructor(Account.class,
                        userEntity.id.as("id"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        Expressions.asEnum(Duty.OUT).as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.isActive.as("isActive"),
                        userEntity.visitDate.as("visitDate"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(userEntity)
                .where(userEntity.isActive.isFalse())
                .orderBy(userEntity.name.asc())
                .fetch();
    }

    public List<User> findAllActiveUsers() {
        List<User> users = queryFactory
                .select(Projections.constructor(User.class,
                        userEntity.id.as("id"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        teamMember.duty.as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.isActive.as("isActive"),
                        userEntity.visitDate.as("visitDate"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(term)
                .join(team).on(team.termId.eq(term.id), term.isActive.isTrue())
                .join(teamMember).on(teamMember.teamId.eq(team.id))
                .join(userEntity).on(userEntity.id.eq(teamMember.memberId))
                .groupBy(userEntity.id)
                .orderBy(userEntity.name.asc())
                .fetch();

        // 제자훈련
        List<User.Discipleship> userDiscipleships = queryFactory
                .select(Projections.constructor(User.Discipleship.class,
                        discipleship.name.as("name"),
                        discipleshipMember.userId.as("userId")
                ))
                .from(discipleship)
                .join(discipleshipMember).on(discipleshipMember.discipleShipId.eq(discipleship.id))
                .fetch();

        Map<Long, List<User.Discipleship>> userIdToDiscipleships = userDiscipleships.stream()
                .collect(Collectors.groupingBy(User.Discipleship::getUserId));

        // 기타 훈련
        List<User.UserTraining> userTrainings = queryFactory
                .select(Projections.constructor(User.UserTraining.class,
                        training.name.as("name"),
                        trainingMember.userId.as("userId"),
                        training.type.as("type")
                ))
                .from(training)
                .join(trainingMember).on(trainingMember.trainingId.eq(training.id))
                .fetch();

        Map<Long, List<User.UserTraining>> userIdToUserTrainings = userTrainings.stream()
                .collect(Collectors.groupingBy(User.UserTraining::getUserId));

        users.forEach(user -> {
            List<User.UserTraining> trainings = userIdToUserTrainings.get(user.getId());
            List<User.Discipleship> discipeships = userIdToDiscipleships.get(user.getId());

            if (trainings != null) {
                user.setTrainings(trainings);
            }

            if (discipeships != null) {
                user.setDiscipleship(discipeships.get(0));
            }
        });

        return users;
    }

    public Account findAccountById(Long id) {
        return queryFactory
                .select(Projections.constructor(Account.class,
                        account.id.as("id"),
                        user.name.as("name"),
                        Expressions.asNumber(1).as("grade"),
                        user.sex.as("sex"),
                        Expressions.asEnum(Duty.GANSA).as("duty"),
                        Expressions.asEnum(Role.ADMIN).as("role"),
                        user.phoneNumber.as("phoneNumber"),
                        user.birth.as("birth"),
                        Expressions.asBoolean(true).as("isActive"),
                        Expressions.asDate(LocalDate.of(1970, 1, 1)).as("visitDate"),
                        Expressions.asString("").as("etc"),
                        user.updatedAt.as("updatedAt"),
                        user.updatedBy.as("updatedBy")
                ))
                .from(account)
                .join(user).on(user.id.eq(account.userId), account.id.eq(id))
                .fetchOne();
    }

    public User findUserById(Long userId) {
        User user = queryFactory
                .select(Projections.constructor(User.class,
                        userEntity.id.as("id"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        teamMember.duty.as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.isActive.as("isActive"),
                        userEntity.visitDate.as("visitDate"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(userEntity)
                .leftJoin(teamMember).on((teamMember.memberId.eq(userEntity.id)), userEntity.id.eq(userId))
                .join(team).on(team.id.eq(teamMember.teamId))
                .join(term).on(term.id.eq(team.termId), term.isActive.isTrue())
                .fetchOne();

        User.Discipleship userDiscipleship = queryFactory
                .select(Projections.constructor(User.Discipleship.class,
                        discipleship.name.as("name"),
                        discipleshipMember.userId.as("userId")
                ))
                .from(discipleship)
                .join(discipleshipMember).on(discipleshipMember.discipleShipId.eq(discipleship.id), discipleshipMember.userId.eq(userId))
                .fetchOne();

        List<User.UserTraining> userTrainings = queryFactory
                .select(Projections.constructor(User.UserTraining.class,
                        training.name.as("name"),
                        trainingMember.userId.as("userId"),
                        training.type.as("type")
                ))
                .from(training)
                .join(trainingMember).on(trainingMember.trainingId.eq(training.id), trainingMember.userId.eq(userId))
                .fetch();

        if (userDiscipleship != null) {
            user.setDiscipleship(userDiscipleship);
        }

        user.setTrainings(userTrainings);

        return user;
    }
}
