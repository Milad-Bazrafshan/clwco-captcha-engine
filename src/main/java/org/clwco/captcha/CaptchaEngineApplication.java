package org.clwco.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CaptchaEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaEngineApplication.class, args);
	}

}
