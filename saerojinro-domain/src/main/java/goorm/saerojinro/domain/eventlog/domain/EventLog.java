package goorm.saerojinro.domain.eventlog.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.eventlog.domain.enums.EventLogType;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EventLog {
	@Id
	private String record;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "lecture_id", nullable = false)
	private Lecture lecture;

	@Enumerated(STRING)
	@Column(nullable = false)
	private EventLogType eventLogType;

	@Enumerated(STRING)
	@Column(nullable = false)
	private Category category;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	public static EventLog create(String record, User user, Lecture lecture, EventLogType eventLogType, Category category) {
		return EventLog.builder()
			.record(record)
			.user(user)
			.lecture(lecture)
			.eventLogType(eventLogType)
			.category(category)
			.timestamp(LocalDateTime.now())
			.build();
	}
}
