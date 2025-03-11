package timetable;

import goorm.saerojinro.api.timetable.application.TimetableFacade;
import goorm.saerojinro.api.timetable.presentation.response.TimetableResponse;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeSpeakerRepository;
import mock.repository.FakeUserRepository;
import mock.repository.FakeWishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TimetableFacadeTest {
	private TimetableFacade timetableFacade;

	@BeforeEach
	public void init() {
		// Facade 준비
		UserRepository userRepository = new FakeUserRepository();
		ReservationRepository reservationRepository = new FakeReservationRepository();
		WishListRepository wishListRepository = new FakeWishListRepository();

		UserQueryService userQueryService = new UserQueryService(userRepository, new BCryptPasswordEncoder());
		ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);
		WishListQueryService wishListQueryService = new WishListQueryService(wishListRepository);

		timetableFacade = new TimetableFacade(userQueryService, reservationQueryService, wishListQueryService);

		// 데이터 준비
		User user = userRepository.save(
			User.builder().build()
		);

		SpeakerRepository speakerRepository = new FakeSpeakerRepository();
		Speaker speaker = speakerRepository.save(Speaker.builder().name("콜 팔머").build());

		LectureRepository lectureRepository = new FakeLectureRepository();
		Lecture lecture1 = lectureRepository.save(
			Lecture.builder()
				.title("Lecture 1")
				.startTime(LocalDateTime.parse("2025-01-01T00:00:00"))
				.endTime(LocalDateTime.parse("2025-01-01T01:00:00"))
				.maxCapacity(10L)
				.location("Room 1")
				.speaker(speaker)
				.build()
		);

		Lecture lecture2 = lectureRepository.save(
			Lecture.builder()
				.title("Lecture 2")
				.startTime(LocalDateTime.parse("2025-01-01T02:00:00"))
				.endTime(LocalDateTime.parse("2025-01-01T03:00:00"))
				.maxCapacity(10L)
				.location("Room 1")
				.speaker(speaker)
				.build()
		);

		reservationRepository.save(
			Reservation.builder().user(user).lecture(lecture1).build()
		);

		wishListRepository.save(
			WishList.builder().user(user).lecture(lecture2).build()
		);
	}

	@Test
	@DisplayName("getTimetable은 사용자의 예약, 즐겨찾기 리스트를 반환한다")
	public void getTimetable_Success() {
		// given

		// when
		TimetableResponse timetable = timetableFacade.getTimetable(1L);

		// then
		assertNotNull(timetable);
		assertEquals(1, timetable.reservation().size());
		assertEquals("Lecture 1", timetable.reservation().get(0).title());
		assertEquals(LocalDateTime.parse("2025-01-01T00:00:00"),  timetable.reservation().get(0).startTime());
		assertEquals(LocalDateTime.parse("2025-01-01T01:00:00"),  timetable.reservation().get(0).endTime());
		assertEquals(1, timetable.wishlist().size());
		assertEquals(10, timetable.wishlist().get(0).capacity());
		assertEquals("Room 1", timetable.wishlist().get(0).location());
		assertEquals("콜 팔머", timetable.wishlist().get(0).speakerName());
	}
}
