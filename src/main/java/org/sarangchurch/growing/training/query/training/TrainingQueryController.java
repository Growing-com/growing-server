package org.sarangchurch.growing.training.query.training;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingQueryController {
    private final TrainingQueryRepository trainingQueryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/training")
    public ApiResponse<List<Training>> findByType(
            @RequestParam TrainingType type
    ) {
        return ApiResponse.of(trainingQueryRepository.findByType(type));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/trainings/{trainingId}")
    public ApiResponse<Training> findById(@PathVariable Long trainingId) {
        return ApiResponse.of(trainingQueryRepository.findById(trainingId));
    }
}
