package org.sarangchurch.growing.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.auth.security.handler.Http401Handler;
import org.sarangchurch.growing.auth.security.handler.Http403Handler;
import org.sarangchurch.growing.auth.security.handler.LoginFailHandler;
import org.sarangchurch.growing.auth.security.handler.LoginSuccessHandler;
import org.sarangchurch.growing.v1.feat.auth.domain.Account;
import org.sarangchurch.growing.v1.feat.auth.domain.AccountRepository;
import org.sarangchurch.growing.v1.feat.auth.domain.Principal;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().rememberMeServices(rememberMeServices())
                .and()

                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/logout").permitAll()
                .antMatchers("/api/auth/isLoggedIn").permitAll()
                .antMatchers("/api/common/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/api/v2/new-family-attendance").authenticated()
                .antMatchers("/api/v2/**").permitAll() // 개발 편의를 위함
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new Http401Handler(objectMapper))
                .accessDeniedHandler(new Http403Handler(objectMapper));

        return http.build();
    }

    @Bean
    AuthFilter authFilter() {
        AuthFilter filter = new AuthFilter("/api/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        filter.setRememberMeServices(rememberMeServices());
        return filter;
    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    TokenBasedRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(
                "rememberMe", userDetailsService());
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setCookieName("rememberMe");
        rememberMeServices.setTokenValiditySeconds(3600 * 24 * 30);
        return rememberMeServices;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Account account = accountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No username: " + username));

            return Principal.from(account);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
