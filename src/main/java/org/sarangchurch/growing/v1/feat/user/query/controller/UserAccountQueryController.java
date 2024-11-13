package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.auth.domain.Principal;
import org.sarangchurch.growing.v1.feat.user.query.model.UserAccount;
import org.sarangchurch.growing.v1.feat.user.query.repository.UserAccountQueryRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountQueryController {
    private final UserAccountQueryRepository userAccountQueryRepository;

    @GetMapping("/api/v1/my-info")
    public ApiResponse<UserAccount> findMyAccount(
            @AuthenticationPrincipal Principal principal
    ) {
        return ApiResponse.of(userAccountQueryRepository.findByUserId(principal.getUserId()));
    }
}
