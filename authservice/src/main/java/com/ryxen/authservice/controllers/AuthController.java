package com.ryxen.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ryxen.authservice.entities.UserRequest;
import com.ryxen.authservice.entities.AuthResponse;
import com.ryxen.authservice.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	  @Autowired
	  private  AuthService authService;


	    @PostMapping(value = "/register")
	    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest authRequest) {
	        return ResponseEntity.ok(authService.register(authRequest));
	    }
	    
	   @PostMapping(value = "/handle-login")
	   public ResponseEntity<AuthResponse> handleLogin(@RequestBody UserRequest authRequest){
		   return ResponseEntity.ok(authService.handleLogin(authRequest));
	   }
	    
	    @PostMapping(value = "/validate")
	    @ResponseStatus(code = HttpStatus.OK)
	    public Boolean validate(@RequestBody String token) {
	        return authService.isValidToken(token);
	    }
	    @PostMapping(value = "/heloo")
	    @ResponseStatus(code = HttpStatus.OK)
	    public String validate() {
	        return "hello";
	    }

}
