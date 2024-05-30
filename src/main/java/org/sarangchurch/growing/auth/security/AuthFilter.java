package org.sarangchurch.growing.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public AuthFilter(String defaultFilterProcessesUrl, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        UsernamePassword usernamePassword = objectMapper.readValue(request.getInputStream(), UsernamePassword.class);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                usernamePassword.username,
                usernamePassword.password
        );

        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Getter
    private static class UsernamePassword {
        private String username;
        private String password;
    }
}
