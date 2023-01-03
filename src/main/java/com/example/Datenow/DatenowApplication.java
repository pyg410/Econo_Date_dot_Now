package com.example.Datenow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 이게 있어야 @CreatedDate가 정상 work
public class DatenowApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";


	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}


	// 현재 프로젝트에서 aws.yml도 사용할 수 있도록 Application.java 코드를 변경
	public static void main(String[] args) {
		//SpringApplication.run(DatenowApplication.class, args);
		new SpringApplicationBuilder(DatenowApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
