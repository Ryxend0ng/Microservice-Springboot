package com.example.gatewayservice.configs;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;

import com.example.gatewayservice.auth.bear.BearerTokenReactiveAuthenticationManager;
import com.example.gatewayservice.auth.bear.ServerHttpBearerAuthenticationConverter;

import reactor.core.publisher.Mono;





@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	@Autowired
	PasswordEncoder pass;

	@Bean
	public SecurityWebFilterChain filter(ServerHttpSecurity  http ) throws Exception {
	
		http.addFilterBefore(bearerAuthenticationFilter(),  SecurityWebFiltersOrder.AUTHENTICATION);
		http.cors().disable();
		http.csrf().disable();
		//http.csrf().csrfTokenRepository(csrfTokenRepository()).
		http.authorizeExchange()
			.pathMatchers("/auth/**").permitAll()
			.pathMatchers("/department/**").hasAnyAuthority("admin")
			.anyExchange().authenticated();		
		return http.build();
	}
	
	
	@SuppressWarnings("deprecation")
	AuthenticationWebFilter bearerAuthenticationFilter() {
		    AuthenticationWebFilter bearerAuthenticationFilter;
	        Function<ServerWebExchange, Mono<Authentication>> bearerConverter;
	        ReactiveAuthenticationManager authManager;
	        
	        authManager  = new BearerTokenReactiveAuthenticationManager();
	        bearerAuthenticationFilter = new AuthenticationWebFilter(authManager);
	        bearerConverter = new ServerHttpBearerAuthenticationConverter();
	        

	        bearerAuthenticationFilter.setAuthenticationConverter(bearerConverter);
	        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

	        return bearerAuthenticationFilter;
	    }
}
