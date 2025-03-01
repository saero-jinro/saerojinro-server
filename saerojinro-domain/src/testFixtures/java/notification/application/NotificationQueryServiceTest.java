package notification.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.notification.exception.NotificationNotFoundException;
import mock.repository.FakeNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationQueryServiceTest {
	private NotificationQueryService notificationQueryService;

	private static final Long LECTURE_ID = 1L;

	private static final String TITLE = "Notification Title";
	private static final String CONTENTS = "Notification Contents";

	@BeforeEach
	public void init() {
		NotificationRepository notificationRepository = new FakeNotificationRepository();
		notificationQueryService = new NotificationQueryService(notificationRepository);

		final String LECTURE_TITLE = "Title";
		final String LECTURE_CONTENTS = "Contents";
		final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
		final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
		final String LOCATION = "Location";
		final Category CATEGORY = Category.BACKEND;
		final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

		Lecture lecture = Lecture.builder()
			.id(LECTURE_ID)
			.title(LECTURE_TITLE)
			.contents(LECTURE_CONTENTS)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.lectureStatus(STATUS)
			.build();

		Notification notification = Notification.createNotification(lecture, TITLE, CONTENTS);
		notificationRepository.save(notification);
	}


	@Test
	@DisplayName("findByLectureId는 notification을 조회한다")
	public void findByLectureId_Success() {
		Notification notification = notificationQueryService.findByLectureId(LECTURE_ID);

		assertEquals(1L, notification.getId());
		assertEquals(TITLE, notification.getTitle());
		assertEquals(CONTENTS, notification.getContents());
	}

	@Test
	@DisplayName("findByLectureId는 lectureId가 존재하지 않을 때 NotificationNotFoundException을 반환한다")
	public void findByLectureId_throws_NotificationNotFoundException() {
		assertThrows(NotificationNotFoundException.class,
			() -> notificationQueryService.findByLectureId(100L));
	}
}
