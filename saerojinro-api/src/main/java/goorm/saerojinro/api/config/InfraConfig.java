package goorm.saerojinro.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import goorm.saerojinro.infra.EnableSaeroJinroConfig;
import goorm.saerojinro.infra.SaeroJinroConfigGroup;

@Configuration(proxyBeanMethods = false)
@ComponentScan("goorm.saerojinro")
@EnableSaeroJinroConfig({
	SaeroJinroConfigGroup.JPA,
	SaeroJinroConfigGroup.JPA_AUDITING,
	SaeroJinroConfigGroup.PROPERTIES
})
class InfraConfig {

}
