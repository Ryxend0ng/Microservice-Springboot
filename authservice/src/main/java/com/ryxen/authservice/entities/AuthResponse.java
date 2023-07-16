package com.ryxen.authservice.entities;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class AuthResponse {
	  private String accessToken;
	    private String refreshToken;
}
