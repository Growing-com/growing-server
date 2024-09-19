package org.sarangchurch.growing.config;

import org.sarangchurch.growing.v1.feat.auth.domain.Principal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class SecurityAuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof Principal) {
                Principal principal = (Principal) authentication.getPrincipal();
                return Optional.of(String.valueOf(principal.getId()));
            }

            return Optional.empty();
        };
    }
}
