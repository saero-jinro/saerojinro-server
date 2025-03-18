package goorm.saerojinro.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SaerojinroAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaerojinroAdminApplication.class, args);
	}

}
