package org.sarangchurch.growing.user.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.auth.security.UserDetailsImpl;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQueryRepository userQueryRepository;

    // TODO: 나중에 accounts 로 엔드 포인트 변경하기
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/user")
    public ApiResponse<List<Account>> findAllAccounts() {
        return ApiResponse.of(userQueryRepository.findAllAccounts());
    }

    // TODO: 나중에 엔드 포인트 변경하기
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/inactiveAccounts")
    public ApiResponse<List<Account>> findAllInactiveAccounts() {
        return ApiResponse.of(userQueryRepository.findAllInactiveAccounts());
    }

    // TODO: 나중에 엔드 포인트 변경하기
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/activeUsers")
    public ApiResponse<List<User>> findAllActiveUsers() {
        return ApiResponse.of(userQueryRepository.findAllActiveUsers());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/user/{accountId}")
    public ApiResponse<Account> findById(@PathVariable Long accountId) {
        return ApiResponse.of(userQueryRepository.findAccountById(accountId));
    }

    @GetMapping("/api/user/myInfo")
    public ApiResponse<Account> findMyInfo(@AuthenticationPrincipal UserDetailsImpl user) {
        return ApiResponse.of(userQueryRepository.findAccountById(user.getId()));
    }
}
