package com.example.gatewayservice.utils;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
@PropertySource("classpath:application.properties")
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret="BvPHGM8C0ia4uOuxxqPD5DTbWC9F9TWvPStp3pb7ARo0oK2mJ3pd3YG4lxA9i8bj6OTbadwezxgeEByY";
    private String tokenJwt;
    private Key key= Keys.hmacShaKeyFor(secret.getBytes());;

//    @PostConstruct
//    public void init(){
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//    }
    public void setTokenJwt(String token) {
    	this.tokenJwt=token;
    }
    public Claims getAllClaimsFromToken() {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenJwt).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken().getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
   
    @SuppressWarnings("deprecation")
	public boolean isValidToken(String authToken) {
    	try {
            Jws<Claims>jws= Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	 System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	 System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	 System.out.println("JWT claims string is empty.");
        }
        return false;
    }
    public Mono<JwtUtils> check(String token){
    	this.tokenJwt=token;
    	if(isValidToken(token)) {
    		return Mono.justOrEmpty(this);
    	}else {
    		return Mono.empty();
    	}
    	
    }
}
