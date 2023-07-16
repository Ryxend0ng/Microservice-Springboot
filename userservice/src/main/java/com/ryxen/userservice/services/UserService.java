package com.ryxen.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ryxen.userservice.entities.UserEntity;
import com.ryxen.userservice.respositories.UserRepository;
@Service
public class UserService {
	 private final UserRepository repository;
	 private final PasswordEncoder encode;
	    @Autowired
	    public UserService(UserRepository repository,PasswordEncoder encode
	                       ) {
	        this.repository = repository;
	        this.encode=encode;
	        
	    }
	    public UserEntity save(UserEntity user) {
	        return this.repository.save(user);
	    }

	    public UserEntity getById(int id) {
	        return this.repository.findById(id).orElse(null);
	    }
	    public UserEntity findByUsernameAndPassword(String username,String password) {
	    	
	    	UserEntity user= repository.findByUsername(username);
	    	if(user!=null) {
	    		boolean passwordDecode=encode.matches(password,user.getPassword());
	    		if(passwordDecode) {
	    			return user;
	    		}
	    	}
	    	return null;
	    }
	  
}
