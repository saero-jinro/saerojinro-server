package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationListResponse(
	Long reservationId,
	Long userId,
	Long lectureId,
	String title,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int currentReservation,
	int capacity,
	String location,
	String speakerName
) {
	public static ReservationListResponse from(Reservation reservation, int currentReservation) {
		Lecture lecture = reservation.getLecture();

		return ReservationListResponse.builder()
			.reservationId(reservation.getId())
			.userId(reservation.getUser().getId())
			.lectureId(lecture.getId())
			.title(lecture.getTitle())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.currentReservation(currentReservation)
			.capacity((int) (lecture.getMaxCapacity() * 0.8))
			.location(lecture.getLocation())
			.speakerName(lecture.getSpeaker().getName())
			.build();
	}
}
