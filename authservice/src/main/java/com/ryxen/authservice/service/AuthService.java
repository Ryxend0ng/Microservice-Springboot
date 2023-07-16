package com.ryxen.authservice.service;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.ryxen.authservice.entities.UserRequest;
import com.ryxen.authservice.exception.UserNotFoundException;
import com.ryxen.authservice.entities.AuthResponse;
import com.ryxen.authservice.entities.UserRequest;
import com.ryxen.authservice.utils.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class AuthService {
	  private final RestTemplate restTemplate;
	  private final JwtUtil jwt;
	  private final PasswordEncoder encode;
	  private  AuthResponse authResponse;
	    @Autowired
	    public AuthService(RestTemplate restTemplate, final JwtUtil jwt,PasswordEncoder encode,AuthResponse auth) {
	        this.restTemplate = restTemplate;
	        this.jwt = jwt;
	        this.encode=encode;
	        this.authResponse=auth;
	    }

	    public AuthResponse register(UserRequest authRequest) {
	        //do validation if user already exists
	        authRequest.setPassword(encode.encode(authRequest.getPassword()));
	       
	        UserRequest userVO = restTemplate.postForObject("http://user-service/users/register", authRequest, UserRequest.class);
	        //UserRequest userVO=authRequest;
	        String accessToken = jwt.generate(userVO, "ACCESS");
	        String refreshToken = jwt.generate(userVO, "REFRESH");

	        return authResponse=new AuthResponse(accessToken, refreshToken);

	    }
	    public AuthResponse handleLogin(UserRequest authRequest)  {
	        UserRequest userVO = restTemplate.postForObject("http://user-service/users/get-user", authRequest, UserRequest.class);
	    	//UserRequest userVO=authRequest;
	        if(userVO==null) {
	        	throw new UserNotFoundException("Not found user");
	        }
	    	String accessToken = jwt.generate(userVO, "ACCESS");
	        String refreshToken = jwt.generate(userVO, "REFRESH");

	        return authResponse=new AuthResponse(accessToken, refreshToken);

	    }
	    
	    public boolean isValidToken(String token) {
	    	
	    	Claims tokenRequest=jwt.getAllClaimsFromToken(token);
	    	Claims tokenCurrent=jwt.getAllClaimsFromToken(authResponse.getAccessToken());
	    	
	    	if(tokenRequest.getSubject().equals(tokenCurrent.getSubject())
	    			&& tokenRequest.get("roles").equals(tokenCurrent.get("roles"))
	    			) {
	    		return true;
	    		
	    	}
	    	return false;
	    	
	    }
	   
}
