package goorm.saerojinro.domain.notification.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "notification")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Notification extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id", nullable = false, updatable = false)
	private Lecture lecture;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	public static Notification createNotification(Lecture lecture, String title, String contents) {
		return Notification.builder()
			.lecture(lecture)
			.title(title)
			.contents(contents)
			.build();
	}
}
