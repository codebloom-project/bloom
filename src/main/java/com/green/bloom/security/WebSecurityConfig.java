package com.green.bloom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final AuthenticationFailureHandler customFailureHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(13);
	}
	
	@Bean
	UserDetailsService empDetailsService() {
		return new EmployeeDetailsService();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable())

		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/img/**", "/js/**","/sign/**").permitAll()
				//.requestMatchers("/employee/**").hasRole("SUPERVISOR")
				.requestMatchers("/**").authenticated()
				.anyRequest().authenticated())
		
		.formLogin(formLogin -> formLogin
				.loginPage("/sign")
				.loginProcessingUrl("/sign/signin")
				.usernameParameter("empUsername")
				.passwordParameter("empPassword")
				.defaultSuccessUrl("/")
				.failureHandler(customFailureHandler)
				.permitAll())
		
		.logout(logout -> logout
				.logoutSuccessUrl("/sign")
				.invalidateHttpSession(true))
		
		.exceptionHandling(accessDenied -> accessDenied
				.accessDeniedPage("/accessDenied"));
		
		return http.build();
	}
}