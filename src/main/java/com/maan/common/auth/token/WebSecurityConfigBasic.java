package com.maan.common.auth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 

@Configuration
@EnableWebSecurity
@Order(999)
public class WebSecurityConfigBasic extends WebSecurityConfigurerAdapter {

	@Autowired
	private BasicAuthenticationPoint basicAuthenticationPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.requestMatchers().antMatchers("/post/notification/**")
            .and()
            .authorizeRequests().anyRequest().hasRole("USER")
            .and()
            .httpBasic();
	
		http.cors();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);
	}
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("nonmotor").password(bCryptPasswordEncoder.encode("nonmotor123#")).roles("USER");
	}
}
