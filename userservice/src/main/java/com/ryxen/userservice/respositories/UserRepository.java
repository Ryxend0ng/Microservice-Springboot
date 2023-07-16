package com.ryxen.userservice.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryxen.userservice.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	public UserEntity findByUsername(String username);
}
