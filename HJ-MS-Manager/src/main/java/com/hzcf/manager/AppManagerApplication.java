package com.hzcf.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class AppManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppManagerApplication.class, args);
	}
}
