package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupService;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport.MemberVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport.MemberVisionReportRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus.ABSENT;
import static org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus.ATTEND;

@Configuration
@RequiredArgsConstructor
public class MemberVisionReportStepConfig {
    private static final String STEP_NAME = "memberVisionReportStep";

    private final TermService termService;
    private final SmallGroupService smallGroupService;
    private final NewFamilyGroupService newFamilyGroupService;
    private final SmallGroupMemberService smallGroupMemberService;
    private final NewFamilyGroupMemberService newFamilyGroupMemberService;

    private final AttendanceRepository attendanceRepository;
    private final MemberVisionReportRepository memberVisionReportRepository;

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;

    @JobScope
    @Bean(STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Sunday sunday = jobParameter.getSunday();

                    LocalDate thisWeek = sunday.getDate();
                    LocalDate prevWeek = sunday.previous().getDate();

                    List<Attendance> thisWeekAttendances = attendanceRepository.findByDate(thisWeek);
                    List<Attendance> prevWeekAttendances = attendanceRepository.findByDate(prevWeek);

                    Term activeTerm = termService.findActiveTerm();
                    List<SmallGroup> smallGroups = smallGroupService.findByTermId(activeTerm.getId());
                    List<NewFamilyGroup> newFamilyGroups = newFamilyGroupService.findByTermId(activeTerm.getId());
                    List<SmallGroupMember> smallGroupMembers = smallGroupMemberService.findBySmallGroupIdIn(
                            smallGroups.stream().map(SmallGroup::getId).collect(Collectors.toList())
                    );
                    List<NewFamilyGroupMember> newFamilyGroupMembers = newFamilyGroupMemberService.findByNewFamilyGroupIdIn(
                            newFamilyGroups.stream().map(NewFamilyGroup::getId).collect(Collectors.toList())
                    );

                    List<Attendance> candidateAttendances = thisWeekAttendances.stream()
                            .filter(thisWeekAttendance -> {
                                Optional<Attendance> optionalPrevWeekAttendance = prevWeekAttendances.stream()
                                        .filter(prevWeekAttendance -> prevWeekAttendance.getUserId().equals(thisWeekAttendance.getUserId()))
                                        .findFirst();

                                if (optionalPrevWeekAttendance.isEmpty()) {
                                    return false;
                                }

                                Attendance prevWeekAttendance = optionalPrevWeekAttendance.get();

                                return prevWeekAttendance.statusEquals(ATTEND) && thisWeekAttendance.statusEquals(ABSENT);
                            })
                            .collect(Collectors.toList());

                    List<MemberVisionReport> memberVisionReports = candidateAttendances.stream()
                            .map(candidateAttendance -> mapMemberVisionReport(
                                    candidateAttendance,
                                    smallGroups,
                                    newFamilyGroups,
                                    smallGroupMembers,
                                    newFamilyGroupMembers,
                                    thisWeek
                            ))
                            .collect(Collectors.toList());

                    memberVisionReportRepository.deleteByDate(thisWeek);
                    memberVisionReportRepository.saveAll(memberVisionReports);

                    stepContribution.setExitStatus(new ExitStatus("SUCCESS", "비전 리포트가 갱신되었습니다. 날짜: " + thisWeek.toString()));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private MemberVisionReport mapMemberVisionReport(
            Attendance candidateAttendance,
            List<SmallGroup> smallGroups,
            List<NewFamilyGroup> newFamilyGroups,
            List<SmallGroupMember> smallGroupMembers,
            List<NewFamilyGroupMember> newFamilyGroupMembers,
            LocalDate thisWeek
    ) {
        Long userId = candidateAttendance.getUserId();

        Optional<SmallGroup> optionalSmallGroup = smallGroups.stream().filter(it -> it.getLeaderUserId().equals(userId)).findFirst();

        if (optionalSmallGroup.isPresent()) {
            SmallGroup smallGroup = optionalSmallGroup.get();

            return MemberVisionReport.builder()
                    .date(thisWeek)
                    .userId(userId)
                    .smallGroupId(smallGroup.getId())
                    .newFamilyGroupId(null)
                    .codyId(smallGroup.getCodyId())
                    .build();
        }

        Optional<NewFamilyGroup> optionalNewFamilyGroup = newFamilyGroups.stream().filter(it -> it.getLeaderUserId().equals(userId)).findFirst();

        if (optionalNewFamilyGroup.isPresent()) {
            NewFamilyGroup newFamilyGroup = optionalNewFamilyGroup.get();

            return MemberVisionReport.builder()
                    .date(thisWeek)
                    .userId(userId)
                    .smallGroupId(null)
                    .newFamilyGroupId(newFamilyGroup.getId())
                    .codyId(newFamilyGroup.getCodyId())
                    .build();
        }

        Optional<SmallGroupMember> optionalSmallGroupMember = smallGroupMembers.stream().filter(it -> it.getUserId().equals(userId)).findFirst();

        if (optionalSmallGroupMember.isPresent()) {
            SmallGroupMember smallGroupMember = optionalSmallGroupMember.get();

            SmallGroup smallGroup = smallGroups.stream()
                    .filter(it -> it.getId().equals(smallGroupMember.getSmallGroupId()))
                    .findFirst()
                    .orElseThrow();

            return MemberVisionReport.builder()
                    .date(thisWeek)
                    .userId(userId)
                    .smallGroupId(smallGroup.getId())
                    .newFamilyGroupId(null)
                    .codyId(smallGroup.getCodyId())
                    .build();
        }

        Optional<NewFamilyGroupMember> optionalNewFamilyGroupMember = newFamilyGroupMembers.stream().filter(it -> it.getUserId().equals(userId)).findFirst();

        if (optionalNewFamilyGroupMember.isPresent()) {
            NewFamilyGroupMember newFamilyGroupMember = optionalNewFamilyGroupMember.get();

            NewFamilyGroup newFamilyGroup = newFamilyGroups.stream()
                    .filter(it -> it.getId().equals(newFamilyGroupMember.getNewFamilyGroupId()))
                    .findFirst()
                    .orElseThrow();

            return MemberVisionReport.builder()
                    .date(thisWeek)
                    .userId(userId)
                    .smallGroupId(null)
                    .newFamilyGroupId(newFamilyGroup.getId())
                    .codyId(newFamilyGroup.getCodyId())
                    .build();
        }

        return null;
    }
}
