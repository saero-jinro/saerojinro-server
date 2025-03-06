package goorm.saerojinro.domain.lecture.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.lecture.exception.LectureDeleteNotAuthorizedException;
import goorm.saerojinro.domain.lecture.exception.LectureUpdateNotAuthorizedException;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.*;
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
			.lectureStatus(PENDING_APPROVAL)
			.build();
	}

	/**
	 * 업데이트 요청 유저는 강연자(본인)이거나 운영자
	 */
	public void update(User speaker, String title, String contents) {
		validateUpdatePermission(speaker);
		this.title = title;
		this.contents = contents;
	}

	/**
	 * 강연자(본인)인 경우 삭제 요청만으로 상태를 DELETE_PENDING
	 * 운영자(ADMIN)인 경우 바로 DELETED 상태로 변경
	 */
	public void requestDelete(User user) {
		if (user.getRole().equals(ADMIN)) {
			this.lectureStatus = DELETED;
			this.delete();
		} else if (this.speaker.getId().equals(user.getId())) {
			this.lectureStatus = PENDING_DELETION;
		} else {
			throw new LectureDeleteNotAuthorizedException();
		}
	}

	private void validateUpdatePermission(User speaker) {
		if (!speaker.getRole().equals(ADMIN) && !(this.speaker.getId().equals(speaker.getId()))) {
			throw new LectureUpdateNotAuthorizedException();
		}
	}
}
