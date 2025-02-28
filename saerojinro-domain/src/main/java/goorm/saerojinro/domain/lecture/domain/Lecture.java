package goorm.saerojinro.domain.lecture.domain;

import goorm.saerojinro.common.entity.BaseTimeEntity;
import goorm.saerojinro.common.model.Category;
import goorm.saerojinro.common.model.LectureStatus;
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
	private Long lectureId;

	private Long userId;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	private String contents;

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

	public static Lecture createLecture(String title, String contents, LocalDateTime startTime, LocalDateTime endTime,
										String location, Category category, LectureStatus lectureStatus) {
		return Lecture.builder()
			.title(title)
			.contents(contents)
			.startTime(startTime)
			.endTime(endTime)
			.location(location)
			.category(category)
			.lectureStatus(lectureStatus)
			.build();
	}
}
