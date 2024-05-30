package org.sarangchurch.growing.term.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.term.domain.team.QTeam;
import org.sarangchurch.growing.term.domain.team.TeamType;
import org.sarangchurch.growing.user.domain.QUserEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.sarangchurch.growing.term.domain.newComerHistory.QNewComerHistory.newComerHistory;
import static org.sarangchurch.growing.term.domain.team.QTeam.team;
import static org.sarangchurch.growing.term.domain.team.QTeamMember.teamMember;
import static org.sarangchurch.growing.term.domain.term.QTerm.term;
import static org.sarangchurch.growing.user.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class TermQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Cody> findCodiesByTerm(Long termId) {
        return queryFactory
                .select(Projections.constructor(Cody.class,
                        userEntity.id.as("userId"),
                        userEntity.name.as("name")
                )).distinct()
                .from(team)
                .join(userEntity).on(
                        team.managerId.eq(userEntity.id),
                        team.termId.eq(termId),
                        team.type.eq(TeamType.PLANT).or(team.type.eq(TeamType.LAMP)))
                .fetch();
    }

    public List<NewTeam> findNewTeamLeadersByTerm(Long termId) {
        return queryFactory
                .select(Projections.constructor(NewTeam.class,
                        team.id.as("teamId"),
                        userEntity.name.as("leaderName")
                )).distinct()
                .from(team)
                .join(userEntity).on(team.leaderId.eq(userEntity.id), team.termId.eq(termId), team.type.eq(TeamType.NEW))
                .fetch();
    }

    public List<Leaders> findLeadersOfCodyByTerm(Long termId, Long codyId) {
        QUserEntity leader = new QUserEntity("leader");

        return queryFactory
                .select(Projections.constructor(Leaders.class,
                        team.id.as("teamId"),
                        team.type.as("teamType"),
                        leader.name.as("leaderName")
                ))
                .from(team)
                .join(leader).on(
                        leader.id.eq(team.leaderId),
                        team.termId.eq(termId),
                        team.managerId.eq(codyId)
                )
                .fetch();
    }

    public List<NewComer> findNewComersByTerm(Long termId) {
        QUserEntity newTeamLeader = new QUserEntity("newTeamLeader");
        QUserEntity plantTeamLeader = new QUserEntity("plantTeamLeader");
        QUserEntity plantTeamManager = new QUserEntity("plantTeamManager");

        QTeam newTeam = new QTeam("newTeam");
        QTeam plantTeam = new QTeam("plantTeam");

        List<NewComer> currentNewComers = queryFactory
                .select(Projections.constructor(NewComer.class,
                        userEntity.id.as("userId"),
                        newTeam.id.as("teamId"),
                        teamMember.id.as("teamMemberId"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        Expressions.asEnum(Duty.NEW_COMER).as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.visitDate.as("visitDate"),
                        Expressions.asDate(LocalDate.of(1970,1,1)).as("lineupDate"),
                        Expressions.asDate(LocalDate.of(1970,1,1)).as("lineoutDate"),
                        newTeamLeader.name.as("newTeamLeaderName"),
                        newTeamLeader.name.as("firstPlantManagerName"),
                        newTeamLeader.name.coalesce("").as("firstPlantLeaderName"),
                        userEntity.isActive.as("isActive"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(userEntity)
                .join(teamMember).on(
                        teamMember.memberId.eq(userEntity.id),
                        teamMember.duty.eq(Duty.NEW_COMER),
                        userEntity.visitTermId.eq(termId)
                )
                .join(newTeam).on(newTeam.id.eq(teamMember.teamId))
                .join(newTeamLeader).on(newTeamLeader.id.eq(newTeam.leaderId))
                .fetch();

        List<NewComer> pastNewComers = queryFactory
                .select(Projections.constructor(NewComer.class,
                        userEntity.id.as("userId"),
                        newTeam.id.as("teamId"),
                        teamMember.id.as("teamMemberId"),
                        userEntity.name.as("name"),
                        userEntity.grade.as("grade"),
                        userEntity.sex.as("sex"),
                        Expressions.asEnum(Duty.NEW_COMER).as("duty"),
                        userEntity.role.as("role"),
                        userEntity.phoneNumber.as("phoneNumber"),
                        userEntity.birth.as("birth"),
                        userEntity.visitDate.as("visitDate"),
                        newComerHistory.lineupWeek.week.as("lineupDate"),
                        newComerHistory.lineoutWeek.week.as("lineoutDate"),
                        newTeamLeader.name.as("newTeamLeaderName"),
                        plantTeamManager.name.as("firstPlantManagerName"),
                        plantTeamLeader.name.as("firstPlantLeaderName"),
                        userEntity.isActive.as("isActive"),
                        userEntity.etc.as("etc"),
                        userEntity.updatedAt.as("updatedAt"),
                        userEntity.updatedBy.as("updatedBy")
                ))
                .from(userEntity)
                .join(teamMember).on(
                        teamMember.memberId.eq(userEntity.id),
                        teamMember.duty.eq(Duty.MEMBER),
                        userEntity.visitTermId.eq(termId)
                )
                .join(newComerHistory).on(newComerHistory.userId.eq(userEntity.id))
                .join(newTeam).on(newTeam.id.eq(newComerHistory.newComerTeamId))
                .join(newTeamLeader).on(newTeamLeader.id.eq(newTeam.leaderId))
                .join(plantTeam).on(plantTeam.id.eq(newComerHistory.firstPlantTeamId))
                .join(plantTeamLeader).on(plantTeamLeader.id.eq(plantTeam.leaderId))
                .join(plantTeamManager).on(plantTeamManager.id.eq(plantTeam.managerId))
                .fetch();

        List<NewComer> result = new ArrayList<>();
        result.addAll(currentNewComers);
        result.addAll(pastNewComers);

        result.sort(Comparator.comparing(NewComer::getVisitDate));

        return result;
    }

    public List<Term> findAll() {
        return queryFactory
                .select(Projections.constructor(Term.class,
                        term.id.as("id"),
                        term.startDate.as("startDate"),
                        term.endDate.as("endDate"),
                        term.status.as("status"),
                        term.memo.as("memo")
                ))
                .from(term)
                .fetch();
    }

    public TotalUser findTotalUser(Week week) {
        Long totalRegistered = queryFactory
                .select(userEntity.count())
                .from(userEntity)
                .where(userEntity.isActive.eq(true))
                .fetchOne();

        Long totalNewRegistered = queryFactory
                .select(userEntity.count())
                .from(userEntity)
                .where(userEntity.visitDate.eq(week.getWeek()))
                .fetchOne();

        return new TotalUser(totalRegistered, totalNewRegistered);
    }
}
