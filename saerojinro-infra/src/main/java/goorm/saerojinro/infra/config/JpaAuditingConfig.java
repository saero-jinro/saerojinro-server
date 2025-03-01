package goorm.saerojinro.infra.config;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import goorm.saerojinro.infra.SaeroJinroConfig;

@EnableJpaAuditing
public class JpaAuditingConfig implements SaeroJinroConfig {
}
