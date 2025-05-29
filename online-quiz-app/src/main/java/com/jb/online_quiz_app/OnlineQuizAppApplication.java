package com.jb.online_quiz_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineQuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuizAppApplication.class, args);
	}

}
