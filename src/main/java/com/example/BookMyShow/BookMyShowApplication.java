package com.example.BookMyShow;

import com.example.BookMyShow.controllers.UserController;
import com.example.BookMyShow.dtos.SignUpRequestDto;
import com.example.BookMyShow.dtos.SignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;

	public static void main(String[] args) {

		SpringApplication.run(BookMyShowApplication.class, args);
	}
	@Override
	public void run(String[] args) {
		//SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
		System.out.println("Welcome to BookMyShow");
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
		signUpRequestDto.setEmailId("mno@gmail.com");
		signUpRequestDto.setPassword("Pass@123");
		SignUpResponseDto responseDto = userController.signUp(signUpRequestDto);
	}
}
