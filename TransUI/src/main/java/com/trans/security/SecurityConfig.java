package com.trans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trans.security.data.MyUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String REGISTER_URL = "//register";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/";
	
	@Autowired
	MyUserDetailsService userDetailsService;

	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	@Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Require login to access internal pages and configure login form.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    // Not using Spring CSRF here to be able to use plain HTML for the login page
	    http.csrf().disable() // (1)

	            // Register our CustomRequestCache that saves unauthorized access attempts, so
	            // the user is redirected after login.
	            .requestCache().requestCache(new CustomRequestCache()) // (2)

	            // Restrict access to our application.
	            .and()
	            .authorizeRequests()
	            .antMatchers(REGISTER_URL).permitAll()
	            // Allow all flow internal requests.
	            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll() // (3)
				
	            // Allow all requests by logged in users.
	            .anyRequest().permitAll() // (4)

	            // Configure the login page.
	            .and().formLogin().loginPage(LOGIN_URL).permitAll() // (5)
	            .loginProcessingUrl(LOGIN_PROCESSING_URL) // (6)
	            .defaultSuccessUrl("/", true)
	            .failureUrl(LOGIN_FAILURE_URL)

	            // Configure logout
	            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}
	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers(
	            // Vaadin Flow static resources // (1)
	            "/VAADIN/**",

	            // the standard favicon URI
	            "/favicon.ico",

	            // the robots exclusion standard
	            "/robots.txt",

	            // web application manifest // (2)
	            "/manifest.webmanifest",
	            "/sw.js",
	            "/offline-page.html",

	            // (development mode) static resources // (3)
	            "/frontend/**",

	            // (development mode) webjars // (3)
	            "/webjars/**",

	            // (production mode) static resources // (4)
	            "/frontend-es5/**", "/frontend-es6/**");
	}
}
