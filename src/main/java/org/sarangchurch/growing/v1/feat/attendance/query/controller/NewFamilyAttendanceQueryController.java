package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.query.model.NewFamilyAttendanceListItem;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.NewFamilyAttendanceQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryController {
    private final NewFamilyAttendanceQueryRepository newFamilyAttendanceQueryRepository;

    @GetMapping("/api/v1/new-families/attendances")
    public ApiResponse<List<NewFamilyAttendanceListItem>> findNewFamilyAttendances() {
        return ApiResponse.of(newFamilyAttendanceQueryRepository.findNewFamilyAttendances());
    }
}
