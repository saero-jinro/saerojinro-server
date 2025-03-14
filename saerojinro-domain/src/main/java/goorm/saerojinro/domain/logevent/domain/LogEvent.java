package goorm.saerojinro.domain.logevent.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.logevent.domain.enums.LogEventType;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(
	indexes = {
		@Index(name = "idx_user_log", columnList = "userId, timestamp DESC")
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LogEvent {
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
	private LogEventType logEventType;

	@Enumerated(STRING)
	@Column(nullable = false)
	private Category category;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	public static LogEvent create(String record, User user, Lecture lecture, LogEventType logEventType, Category category) {
		return LogEvent.builder()
			.record(record)
			.user(user)
			.lecture(lecture)
			.logEventType(logEventType)
			.category(category)
			.timestamp(LocalDateTime.now())
			.build();
	}
}
