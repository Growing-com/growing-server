package org.sarangchurch.growing.auth.presentation;

import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.auth.security.UserDetailsImpl;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class AuthController {

    @PostMapping("/api/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        Cookie sessionCookie = new Cookie("SESSION", "sid");
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(0);

        Cookie rememberCookie = new Cookie("rememberMe", "0");
        rememberCookie.setPath("/");
        rememberCookie.setHttpOnly(true);
        rememberCookie.setMaxAge(0);

        response.addCookie(sessionCookie);
        response.addCookie(rememberCookie);
    }

    @GetMapping("/api/auth/isLoggedIn")
    public ApiResponse<Boolean> isLoggedIn(@AuthenticationPrincipal UserDetailsImpl user) {
        return ApiResponse.of(user != null);
    }
}
