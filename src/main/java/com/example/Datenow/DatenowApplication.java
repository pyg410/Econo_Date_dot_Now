package com.example.Datenow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 이게 있어야 @CreatedDate가 정상 work
public class DatenowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatenowApplication.class, args);
	}

}
