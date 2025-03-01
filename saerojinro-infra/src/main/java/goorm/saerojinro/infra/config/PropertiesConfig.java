package goorm.saerojinro.infra.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import goorm.saerojinro.infra.SaeroJinroConfig;

@ConfigurationPropertiesScan(basePackages = "goorm.saerojinro")
public class PropertiesConfig implements SaeroJinroConfig {
}
