package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationsResponse(
	Long reservationId,
	Long userId,
	Long lectureId,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int currentReservation,
	int capacity
) {
	public static ReservationsResponse from(Reservation reservation, int currentReservation) {
		return ReservationsResponse.builder()
			.reservationId(reservation.getId())
			.userId(reservation.getUser().getId())
			.lectureId(reservation.getLecture().getId())
			.startTime(reservation.getLecture().getStartTime())
			.endTime(reservation.getLecture().getEndTime())
			.currentReservation(currentReservation)
			.capacity(Math.toIntExact(reservation.getLecture().getMaxCapacity()))
			.build();
	}
}
