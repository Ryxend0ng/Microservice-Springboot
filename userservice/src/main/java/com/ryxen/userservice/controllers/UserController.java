package com.ryxen.userservice.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ryxen.userservice.entities.UserEntity;
import com.ryxen.userservice.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserEntity save(@RequestBody UserEntity user) {
        return userService.save(user);
    }
    @PostMapping("/get-user")
    public UserEntity getUserByUsernameAndPassword(@RequestBody UserEntity user) {
        return userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
    @GetMapping("/{id}")
    public UserEntity getUser(
           @PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
    
    
}
