package goorm.saerojinro.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import goorm.saerojinro.infra.SaeroJinroConfig;

@Configuration
@EntityScan(basePackages = "goorm.saerojinro.domain")
@EnableJpaRepositories(basePackages = "goorm.saerojinro.infra")
public class JpaConfig implements SaeroJinroConfig {

}
