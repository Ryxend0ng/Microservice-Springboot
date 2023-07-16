package com.ryxen.authservice.entities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	   private String username;
	    private String email;
	    private String password;
	    private String role;
	
}
