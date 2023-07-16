package com.example.gatewayservice.auth.bear;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.gatewayservice.utils.JwtUtils;

import java.text.ParseException;
import reactor.core.publisher.Mono;

public class UsernamePasswordAuthenticationBearer {

    @SuppressWarnings("unchecked")
	public static Mono<Authentication> create(JwtUtils jwtUtils) {
        String subject;
        String auths;
        List authorities;

        subject = jwtUtils.getAllClaimsFromToken().getSubject();
		auths = (String) jwtUtils.getAllClaimsFromToken().get("roles");
        authorities = Stream.of(auths.split(","))
                .map(a -> new SimpleGrantedAuthority(a))
                .collect(Collectors.toList());

            return  Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(subject, null, authorities));

    }
}