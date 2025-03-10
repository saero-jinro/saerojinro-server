package goorm.saerojinro.domain.lecture.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static goorm.saerojinro.domain.lecture.enums.LectureStatus.*;

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
	@JoinColumn(name = "speaker_id")
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Enumerated(EnumType.STRING)
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
			.lectureStatus(APPROVED) // 추후 상태 필드 수정
			.build();
	}

	/**
	 * 운영자만 수정 허용
	 */
	public void update(String title, String contents, Long maxCapacity, LocalDateTime startTime,
					   LocalDateTime endTime, String location, Category category) {
		if (title != null) {
			this.title = title;
		}
		if (contents != null) {
			this.contents = contents;
		}
		if (maxCapacity != null) {
			this.maxCapacity = maxCapacity;
		}
		if (startTime != null) {
			this.startTime = startTime;
		}
		if (endTime != null) {
			this.endTime = endTime;
		}
		if (location != null) {
			this.location = location;
		}
		if (category != null) {
			this.category = category;
		}
	}

	/**
	 * 운영자만 삭제 허용
	 */
	@Override
	public void delete() {
		super.delete();
		this.lectureStatus = DELETED;
	}
}
