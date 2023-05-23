package com.thomas.EWD_PROJECT_LIBR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import service.CustomUserDetailsService;
import validator.BoekValidation;

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
	BoekValidation boekValidation() {
		return new BoekValidation();
	}
	@Bean
	public UserDetailsService custom() {
		return new CustomUserDetailsService();
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
}
