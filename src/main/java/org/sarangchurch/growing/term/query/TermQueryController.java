package org.sarangchurch.growing.term.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermQueryController {

    private final TermQueryRepository termQueryRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term/{termId}/cody")
    public ApiResponse<List<Cody>> findCodiesByTerm(@PathVariable Long termId) {
        return ApiResponse.of(termQueryRepository.findCodiesByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term/{termId}/newTeamLeaders")
    public ApiResponse<List<NewTeam>> findNewTeamLeadersByTerm(@PathVariable Long termId) {
        return ApiResponse.of(termQueryRepository.findNewTeamLeadersByTerm(termId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term/{termId}/cody/{codyId}/leaders")
    public ApiResponse<List<Leaders>> findLeadersOfCodyByTerm(
            @PathVariable Long termId,
            @PathVariable Long codyId
    ) {
        return ApiResponse.of(termQueryRepository.findLeadersOfCodyByTerm(termId, codyId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term")
    public ApiResponse<List<Term>> findTerms() {
        return ApiResponse.of(termQueryRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term/totalUser")
    public ApiResponse<TotalUser> totalUser(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate week) {
        return ApiResponse.of(termQueryRepository.findTotalUser(Week.previousSunday(week)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/term/{termId}/newComers")
    public ApiResponse<List<NewComer>> findNewComersByTerm(@PathVariable Long termId) {
        return ApiResponse.of(termQueryRepository.findNewComersByTerm(termId));
    }
}
