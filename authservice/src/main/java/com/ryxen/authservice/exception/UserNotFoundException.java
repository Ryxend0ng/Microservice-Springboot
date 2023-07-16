package com.ryxen.authservice.exception;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mess;
	public UserNotFoundException(String mess) {
		this.mess=mess;
	}
	

}
