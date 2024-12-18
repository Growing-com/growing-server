package org.sarangchurch.growing.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfig implements WebMvcConfigurer {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (activeProfile.equals("dev")) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "https://dev-growing-admin.vercel.app")
                    .allowedMethods("*")
                    .allowCredentials(true);
        } else if (activeProfile.equals("prod")) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "https://growing-admin.vercel.app", "https://dev-growing-admin.vercel.app")
                    // .allowedOrigins("https://growing-admin.vercel.app") // 나중에 이걸로 replace
                    .allowedMethods("*")
                    .allowCredentials(true);
        }
    }
}
