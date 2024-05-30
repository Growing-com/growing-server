package org.sarangchurch.growing.training.query.training;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sarangchurch.growing.training.domain.training.QTraining.training;
import static org.sarangchurch.growing.training.domain.training.QTrainingMember.trainingMember;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class TrainingQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Training> findByType(TrainingType type) {
        return queryFactory.select(Projections.constructor(Training.class,
                        training.id.as("id"),
                        training.name.as("name"),
                        training.type.as("type"),
                        training.startDate.as("startDate"),
                        training.endDate.as("endDate"),
                        training.etc.as("etc")
                ))
                .from(training)
                .where(training.type.eq(type))
                .fetch();
    }

    public Training findById(Long trainingId) {
        Training findTraining = queryFactory.select(Projections.constructor(Training.class,
                        training.id.as("id"),
                        training.name.as("name"),
                        training.type.as("type"),
                        training.startDate.as("startDate"),
                        training.endDate.as("endDate"),
                        training.etc.as("etc")
                ))
                .from(training)
                .where(training.id.eq(trainingId))
                .fetchOne();

        if (findTraining == null) {
            throw new IllegalArgumentException("존재하지 않는 훈련입니다");
        }

        List<Training.TrainingMember> trainingMembers = queryFactory.select(Projections.constructor(Training.TrainingMember.class,
                        userEntity.id.as("userId"),
                        trainingMember.trainingId.as("trainingId"),
                        userEntity.grade.as("grade"),
                        userEntity.name.as("name"),
                        userEntity.sex.as("sex"),
                        userEntity.phoneNumber.as("phoneNumber")
                ))
                .from(trainingMember)
                .join(userEntity).on(userEntity.id.eq(trainingMember.userId))
                .where(trainingMember.trainingId.eq(trainingId))
                .fetch();

        findTraining.setMembers(trainingMembers);

        return findTraining;
    }
}
