package goorm.saerojinro.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	ATTENDEE("참가자"),
	SPEAKER("강연자"),
	ADMIN("운영자"),
	;

	private final String description;
}
