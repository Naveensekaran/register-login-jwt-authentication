package com.Login.LoginAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LoginAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAuthenticationApplication.class, args);
	}

}
