package timetable;

import goorm.saerojinro.api.timetable.application.ReservationCountScheduler;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationCountSchedulerTest {
	private ReservationCountScheduler scheduler;
	private ReservationQueryService queryService;

	private final Long LECTURE_ID = 1L;

	@BeforeEach
	public void init() {
		ReservationRepository reservationRepository = new FakeReservationRepository();

		queryService = new ReservationQueryService(reservationRepository);
		ReservationCommandService commandService = new ReservationCommandService(reservationRepository);
		scheduler = new ReservationCountScheduler(queryService, commandService);

		User user1 = User.builder().id(1L).build();
		User user2 = User.builder().id(2L).build();
		User user3 = User.builder().id(3L).build();

		Lecture lecture = Lecture.builder().id(LECTURE_ID).build();

		reservationRepository.save(Reservation.create(user1, lecture));
		reservationRepository.save(Reservation.create(user2, lecture));
		reservationRepository.save(Reservation.create(user3, lecture));
	}

	@Test
	@DisplayName("updateReservationCount는 레디스에 강의별 예약 수를 저장한다")
	public void updateReservationCount_Success() {
		// when
		scheduler.updateReservationCount();

		// then
		assertEquals(3, queryService.countByLectureIdFromRedis(LECTURE_ID));
	}
}
