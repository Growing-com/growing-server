package org.sarangchurch.growing.v2.feat.newfamily.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyGroupQueryController {
    private final NewFamilyGroupQueryRepository newFamilyGroupQueryRepository;

    @GetMapping("/api/v2/term/{termId}/new-family-groups")
    public ApiResponse<List<NewFamilyGroup>> findNewFamilyGroupsByTerm(@PathVariable Long termId) {
        return ApiResponse.of(newFamilyGroupQueryRepository.findByTerm(termId));
    }
}
