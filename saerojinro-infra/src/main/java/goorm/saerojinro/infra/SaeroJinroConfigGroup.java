package goorm.saerojinro.infra;

import goorm.saerojinro.infra.config.JpaAuditingConfig;
import goorm.saerojinro.infra.config.JpaConfig;
import goorm.saerojinro.infra.config.PropertiesConfig;
import goorm.saerojinro.infra.config.SchedulingConfig;
import goorm.saerojinro.infra.config.redis.AsyncConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SaeroJinroConfigGroup {
	JPA(JpaConfig.class),
	JPA_AUDITING(JpaAuditingConfig.class),
	PROPERTIES(PropertiesConfig.class),
	SCHEDULING(SchedulingConfig.class),
	ASYNCHRONOUS(AsyncConfig.class);
	;

	private final Class<? extends SaeroJinroConfig> configClass;
}
