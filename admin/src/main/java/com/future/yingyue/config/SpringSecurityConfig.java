package com.future.yingyue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/to_login", "/test/**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/to_login").loginProcessingUrl("/login").failureUrl("/to_login?error=true").defaultSuccessUrl("/index",true)
				.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).and().and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/to_login").deleteCookies("JSESSIONID", "BCP").and().csrf()
				.disable().rememberMe().rememberMeCookieName("BCP").useSecureCookie(true)
				.tokenValiditySeconds(3600 * 1);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
	protected static class GlobalMethodSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		@Qualifier("adminDetailsService")
		private UserDetailsService userDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(superPasswordEncoder());
		}

		@Bean
		public PasswordEncoder superPasswordEncoder() {
			return new BCryptPasswordEncoder(4);
		}
	}

}
