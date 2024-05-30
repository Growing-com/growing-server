package org.sarangchurch.growing.discipleship.query.discipleship;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.training.query.discipleship.Discipleship;
import org.sarangchurch.growing.training.query.discipleship.DiscipleshipQueryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiscipleshipQueryController {
    private final DiscipleshipQueryRepository discipleshipQueryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/discipleship")
    public ApiResponse<List<Discipleship>> findAll() {
        return ApiResponse.of(discipleshipQueryRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/discipleship/{discipleshipId}")
    public ApiResponse<Discipleship> findById(@PathVariable Long discipleshipId) {
        return ApiResponse.of(discipleshipQueryRepository.findById(discipleshipId));
    }
}
