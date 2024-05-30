package org.sarangchurch.growing.attendance.query.excel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.query.excel.grade.WeeklyGradeAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.leader.WeeklyLeaderAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.manager.WeeklyManagerAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.personal.WeeklyPersonalAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.rate.TermPersonalAttendanceRateResult;
import org.sarangchurch.growing.core.config.JobRunner;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AttendanceExcelQueryController {
    private final AttendanceExcelQueryRepository attendanceExcelQueryRepository;
    private final JobRunner jobRunner;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/personalAttendance")
    public ApiResponse<List<WeeklyPersonalAttendanceResult>> findAllPersonalAttendanceByTerm(@PathVariable Long termId) {
        return ApiResponse.of(attendanceExcelQueryRepository.findAllPersonalAttendanceByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/newFamilyAttendance")
    public ApiResponse<List<WeeklyPersonalAttendanceResult>> findAllNewFamilyAttendanceByTerm(@PathVariable Long termId) {
        return ApiResponse.of(attendanceExcelQueryRepository.findAllNewFamilyAttendanceByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/leaderAttendance")
    public ApiResponse<List<WeeklyLeaderAttendanceResult>> findAllLeaderAttendanceByTerm(@PathVariable Long termId) {
        return ApiResponse.of(attendanceExcelQueryRepository.findAllLeaderAttendanceByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/managerAttendance")
    public ApiResponse<List<WeeklyManagerAttendanceResult>> findAllManagerAttendanceByTerm(@PathVariable Long termId) {
        return ApiResponse.of(attendanceExcelQueryRepository.findAllManagerAttendanceByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/gradeAttendance")
    public ApiResponse<List<WeeklyGradeAttendanceResult>> findAllGradeAttendanceByTerm(@PathVariable Long termId) {
        return ApiResponse.of(attendanceExcelQueryRepository.findAllGradeAttendanceByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/attendanceRate")
    public ApiResponse<TermPersonalAttendanceRateResult> findAllAttendanceRateByTerm(@PathVariable Long termId) throws Exception {
        jobRunner.run("termAttendanceJob", new JobParametersBuilder()
                .addString("termId", termId.toString())
                .addString("run.id", UUID.randomUUID().toString())
                .toJobParameters());

        return ApiResponse.of(attendanceExcelQueryRepository.findAllAttendanceRateByTerm(termId));
    }

    public void runTermAttendanceJob(Long termId) throws NoSuchJobException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

    }
}
