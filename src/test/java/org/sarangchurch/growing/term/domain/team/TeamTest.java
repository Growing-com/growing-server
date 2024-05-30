package org.sarangchurch.growing.term.domain.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TeamTest {
    @DisplayName("[unit] 일반 순모임에 새가족을 바로 추가할 수 없다.")
    @Test
    void name() {
        Team plantTeam = Team.builder().type(TeamType.PLANT).build();

        assertThatThrownBy(() -> plantTeam.addNewComer(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("새가족반에만 등록할 수 있습니다.");
    }

    @DisplayName("[unit] 새가족반에 새가족을 바로 추가할 수 있다.")
    @Test
    void name2() {
        Team newFamilyTeam = Team.builder()
                .id(5L)
                .type(TeamType.NEW)
                .build();

        assertDoesNotThrow(() -> newFamilyTeam.addNewComer(1L));

        assertThat(newFamilyTeam.getTeamMembers().getTeamMembers())
                .contains(TeamMember.newComer(5L, 1L));
    }

    @DisplayName("[unit] 유저가 순모임에 접근 권한을 가지고 있는지 검사할 수 있다.")
    @Test
    void name3() {
        Team team = Team.builder()
                .leaderId(3L)
                .managerId(4L)
                .build();

        assertThat(team.hasAccess(1L)).isFalse();
        assertThat(team.hasAccess(3L)).isTrue();
        assertThat(team.hasAccess(4L)).isTrue();
        assertThat(team.hasAccess(5L)).isFalse();
    }

    @DisplayName("[unit] 순모임에 속한 조원인지 검사할 수 있다.")
    @Test
    void name134() {
        // given
        Team team = Team.builder().build();

        TeamMember teamMember = TeamMember.newComer(1L, 2L);
        teamMember.id = 3L;

        team.getTeamMembers().getTeamMembers().add(teamMember);

        // expected
        assertThat(team.hasTeamMember(3L)).isTrue();
        assertThat(team.hasTeamMember(4L)).isFalse();
    }

    @DisplayName("[unit] 새가족반이 아니면 라인아웃할 수 없다.")
    @Test
    void name4() {
        Team plantTeam = Team.builder()
                .type(TeamType.PLANT)
                .build();

        Team outTeam = Team.builder()
                .type(TeamType.OUT)
                .build();

        assertThatThrownBy(() -> plantTeam.lineoutNewComerTo(1L, outTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 순모임은 새가족 순모임이 아닙니다.");
    }

    @DisplayName("[unit] 소속되지 않은 새가족은 라인아웃할 수 없다.")
    @Test
    void name55() {
        // given
        Team newFamilyTeam = Team.builder()
                .type(TeamType.NEW)
                .build();

        Team outTeam = Team.builder()
                .type(TeamType.OUT)
                .build();

        TeamMember teamMember = new TeamMember(1L, 2L, Duty.MEMBER);
        teamMember.id = 3L;

        // expected
        assertThatThrownBy(() -> newFamilyTeam.lineoutNewComerTo(3L, outTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("새가족 순모임에 소속되지 않은 조원입니다.");
    }

    @DisplayName("[unit] 새가족이 아니면 라인아웃할 수 없다.")
    @Test
    void name5() {
        // given
        Team newFamilyTeam = Team.builder()
                .type(TeamType.NEW)
                .build();

        Team outTeam = Team.builder()
                .type(TeamType.OUT)
                .build();

        TeamMember teamMember = new TeamMember(1L, 2L, Duty.MEMBER);
        teamMember.id = 3L;

        newFamilyTeam.getTeamMembers().getTeamMembers().add(teamMember);

        // expected
        assertThatThrownBy(() -> newFamilyTeam.lineoutNewComerTo(3L, outTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 조원은 새가족이 아닙니다.");
    }

    @DisplayName("[unit] 라인아웃은 새가족이 라인아웃 모임으로만 될 수 있다.")
    @Test
    void name6() {
        // given
        Team newFamilyTeam = Team.builder()
                .id(100L)
                .type(TeamType.NEW)
                .build();

        Team outTeam = Team.builder()
                .id(200L)
                .type(TeamType.OUT)
                .build();

        TeamMember teamMember = new TeamMember(100L, 300L, Duty.NEW_COMER);
        teamMember.id = 400L;

        newFamilyTeam.getTeamMembers().getTeamMembers().add(teamMember);

        // when
        assertDoesNotThrow(() -> newFamilyTeam.lineoutNewComerTo(400L, outTeam));

        // then
        assertThat(teamMember.getTeamId()).isEqualTo(200L);
        assertThat(teamMember.getDuty()).isEqualTo(Duty.OUT);
    }

    @DisplayName("[unit] 라인아웃은 새가족이 라인아웃 모임으로만 될 수 있다.")
    @Test
    void name8() {
        // given
        Team newFamilyTeam = Team.builder()
                .id(100L)
                .type(TeamType.NEW)
                .build();

        Team plantTeam = Team.builder()
                .id(200L)
                .type(TeamType.PLANT)
                .build();

        TeamMember teamMember = new TeamMember(100L, 300L, Duty.NEW_COMER);
        teamMember.id = 400L;

        newFamilyTeam.getTeamMembers().getTeamMembers().add(teamMember);

        // expected
        assertThatThrownBy(() -> newFamilyTeam.lineoutNewComerTo(400L, plantTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("라인아웃 모임으로만 라인아웃 가능합니다.");
    }

    @DisplayName("[unit] 라인업은 새가족이 일반 순모임으로만 될 수 있다.")
    @Test
    void name7() {
        // given
        Team newFamilyTeam = Team.builder()
                .id(100L)
                .type(TeamType.NEW)
                .build();

        Team plantTeam = Team.builder()
                .id(200L)
                .type(TeamType.PLANT)
                .build();

        TeamMember teamMember = new TeamMember(100L, 300L, Duty.NEW_COMER);
        teamMember.id = 400L;

        newFamilyTeam.getTeamMembers().getTeamMembers().add(teamMember);

        // when
        assertDoesNotThrow(() -> newFamilyTeam.lineupNewComerTo(400L, plantTeam));

        // then
        assertThat(teamMember.getTeamId()).isEqualTo(200L);
        assertThat(teamMember.getDuty()).isEqualTo(Duty.MEMBER);
    }

    @DisplayName("[unit] 라인업은 새가족이 일반 순모임으로만 될 수 있다.")
    @Test
    void name77() {
        // given
        Team newFamilyTeam = Team.builder()
                .id(100L)
                .type(TeamType.NEW)
                .build();

        Team outTeam = Team.builder()
                .id(200L)
                .type(TeamType.OUT)
                .build();

        TeamMember teamMember = new TeamMember(100L, 300L, Duty.NEW_COMER);
        teamMember.id = 400L;

        newFamilyTeam.getTeamMembers().getTeamMembers().add(teamMember);

        // expected
        assertThatThrownBy(() -> newFamilyTeam.lineupNewComerTo(400L, outTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("일반 순모임으로만 라인업 가능합니다.");
    }
}
