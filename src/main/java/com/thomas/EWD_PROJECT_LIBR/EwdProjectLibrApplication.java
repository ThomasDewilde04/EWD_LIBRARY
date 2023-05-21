package com.thomas.EWD_PROJECT_LIBR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import service.CustomUserDetailsService;

@SpringBootApplication
@ComponentScans({
		@ComponentScan("service"),
		@ComponentScan("domein"),
		@ComponentScan("repo"),
})
@EnableJpaRepositories(basePackages = {"repo"})
@EntityScan(basePackages = {"domein"})
public class EwdProjectLibrApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EwdProjectLibrApplication.class, args);

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/library");
	}

	@Bean
	public UserDetailsService custom() {
		return new CustomUserDetailsService();
	}
}
