package goorm.saerojinro.admin.notification.application;

import goorm.saerojinro.common.event.CommonEvent;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static goorm.saerojinro.common.event.EventType.LECTURE_IMMINENT;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {
	private final ApplicationEventPublisher eventPublisher;
	private final LectureQueryService lectureQueryService;
	private final int MINUTES_BEFORE = 5;

	@Scheduled(cron = "0 0/5 * * * *")
	public void checkLectureTime() {
		LocalDateTime fiveMinutesLater = LocalDateTime.now()
			.truncatedTo(MINUTES)
			.plusMinutes(MINUTES_BEFORE);
		System.out.println(fiveMinutesLater);
		List<Lecture> lectureList = lectureQueryService.getAllLectureByStartTime(fiveMinutesLater);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (Lecture lecture : lectureList) {
			eventPublisher.publishEvent(CommonEvent.createWithLectureId(
				LECTURE_IMMINENT,
				lecture.getId(),
				lecture.getTitle() + " 강의 시작이 5분 남았습니다",
				lecture.getTitle() + "이 " +
					lecture.getLocation() + "에서 " +
					fiveMinutesLater.format(formatter) + "에 시작합니다")
			);
		}
		System.out.println("hey");
	}
}
