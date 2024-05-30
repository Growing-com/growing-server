package org.sarangchurch.growing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GrowingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrowingApplication.class, args);
	}

	@Configuration
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000", "https://growing-admin.vercel.app", "https://dev-growing-admin.vercel.app")
					.allowedMethods("*")
					.allowCredentials(true);
		}
	}

}
