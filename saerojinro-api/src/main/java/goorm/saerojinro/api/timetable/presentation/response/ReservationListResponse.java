package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record ReservationListResponse(
	@Schema(description = "예약 id", example = "1", requiredMode = REQUIRED)
	Long reservationId,

	@Schema(description = "참가자 id", example = "1", requiredMode = REQUIRED)
	Long userId,

	@Schema(description = "강의 id", example = "1", requiredMode = REQUIRED)
	Long lectureId,

	@Schema(description = "강의 제목", example = "클린코드란", requiredMode = REQUIRED)
	String title,

	@Schema(description = "시작시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "종료시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	LocalDateTime endTime,

	@Schema(description = "현재 예약 인원", example = "1", requiredMode = REQUIRED)
	int currentReservation,

	@Schema(description = "예약 가능 인원", example = "80", requiredMode = REQUIRED)
	int capacity,

	@Schema(description = "위치", example = "101호", requiredMode = REQUIRED)
	String location,

	@Schema(description = "강연자 이름", example = "마틴 파울러", requiredMode = REQUIRED)
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
