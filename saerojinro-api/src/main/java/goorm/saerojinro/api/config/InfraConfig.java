package goorm.saerojinro.api.config;

import goorm.saerojinro.infra.EnableSaeroJinroConfig;
import goorm.saerojinro.infra.SaeroJinroConfigGroup;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("goorm.saerojinro")
@EnableSaeroJinroConfig({
	SaeroJinroConfigGroup.JPA,
	SaeroJinroConfigGroup.JPA_AUDITING,
	SaeroJinroConfigGroup.PROPERTIES,
})
class InfraConfig {

}
