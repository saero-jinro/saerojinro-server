package goorm.saerojinro.api.timetable.application;

import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.dto.LectureReservationCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationCountScheduler {
	private final ReservationQueryService reservationQueryService;
	private final ReservationCommandService reservationCommandService;

	@Scheduled(cron = "0 */5 * * * *")
	public void updateReservationCount() {
		List<LectureReservationCountDto> all = reservationQueryService.getReservationAllLecture();
		for (LectureReservationCountDto lectureCount : all) {
			System.out.println("hello");
			reservationCommandService.updateReservationNumberInLecture(
				lectureCount.lectureId(), Math.toIntExact(lectureCount.reservationCount()));
		}
	}
}
