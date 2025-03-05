package goorm.saerojinro.domain.lecture.domain;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.lecture.exception.SpeakerMissmatchException;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "lecture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "speaker_id", nullable = false, insertable = false)
	private User speaker;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false)
	private Long maxCapacity;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private LocalDateTime endTime;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private Category category;

	@Column(nullable = false)
	private LectureStatus lectureStatus;

	public static Lecture create(User speaker, String title, String contents, Long maxCapacity,
										LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {
		return Lecture.builder()
			.speaker(speaker)
			.title(title)
			.contents(contents)
			.maxCapacity(maxCapacity)
			.startTime(startTime)
			.endTime(endTime)
			.location(location)
			.category(category)
			.lectureStatus(LectureStatus.PENDING_APPROVAL)
			.build();
	}

	public void update(User speaker, String title, String contents) {
		validateUpdatePermission(speaker);
		this.title = title;
		this.contents = contents;
	}

	private void validateUpdatePermission(User speaker) {
		if (!speaker.getRole().equals(BaseRole.ADMIN) && !(this.speaker.getId().equals(speaker.getId()))) {
			throw new SpeakerMissmatchException();
		}
	}
}
