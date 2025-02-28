package goorm.saerojinro.admin.api;

import org.springframework.stereotype.Component;

import goorm.saerojinro.domain.user.application.UserCommandService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminFacade {
	private final UserCommandService userCommandService;
}
