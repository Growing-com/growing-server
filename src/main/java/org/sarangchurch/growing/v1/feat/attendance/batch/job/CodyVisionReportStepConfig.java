package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.core.interfaces.v1.term.CodyService;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupService;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport.CodyVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport.CodyVisionReportRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
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
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CodyVisionReportStepConfig {
    private static final String STEP_NAME = "codyVisionReportStep";

    private final TermService termService;
    private final CodyService codyService;
    private final SmallGroupService smallGroupService;
    private final NewFamilyGroupService newFamilyGroupService;
    private final SmallGroupMemberService smallGroupMemberService;
    private final NewFamilyGroupMemberService newFamilyGroupMemberService;

    private final CodyVisionReportRepository codyVisionReportRepository;
    private final AttendanceRepository attendanceRepository;

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Sunday sunday = jobParameter.getSunday();
                    LocalDate date = sunday.getDate();

                    Term activeTerm = termService.findActiveTerm();
                    List<Cody> codies = codyService.findByTermId(activeTerm.getId());

                    codyVisionReportRepository.deleteByDate(date);

                    List<CodyVisionReport> codyVisionReports = codies.stream()
                            .map(cody -> {
                                List<SmallGroup> smallGroups = smallGroupService.findByCodyId(cody.getId());
                                List<Long> smallGroupIds = smallGroups.stream().map(SmallGroup::getId).collect(Collectors.toList());
                                long smallGroupMemberCount = smallGroupMemberService.countBySmallGroupIdIn(smallGroupIds);

                                List<NewFamilyGroup> newFamilyGroups = newFamilyGroupService.findByCodyId(cody.getId());
                                List<Long> newFamilyGroupIds = newFamilyGroups.stream().map(NewFamilyGroup::getId).collect(Collectors.toList());
                                long newFamilyGroupMemberCount = newFamilyGroupMemberService.countByNewFamilyGroupIdIn(newFamilyGroupIds);

                                List<Attendance> attendances = attendanceRepository.findByCodyId(cody.getId());
                                long attendCount = attendances.stream()
                                        .filter(it -> it.statusEquals(AttendanceStatus.ATTEND))
                                        .count();

                                return CodyVisionReport.builder()
                                        .date(date)
                                        .codyId(cody.getId())
                                        .total((int) (smallGroupIds.size() +
                                                smallGroupMemberCount +
                                                newFamilyGroupIds.size() +
                                                newFamilyGroupMemberCount)
                                        )
                                        .attend((int) attendCount)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    codyVisionReportRepository.saveAll(codyVisionReports);

                    stepContribution.setExitStatus(new ExitStatus("SUCCESS", "비전 리포트가 갱신되었습니다. 날짜: " + date.toString()));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
