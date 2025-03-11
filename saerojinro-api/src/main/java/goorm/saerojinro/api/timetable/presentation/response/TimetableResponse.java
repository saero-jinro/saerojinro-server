package goorm.saerojinro.api.timetable.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record TimetableResponse(
	@Schema(description = "예약 리스트",
		example = "[{"
			+ "\"reservationId\": 1, "
			+ "\"userId\": 1, "
			+ "\"lectureId\": 1, "
			+ "\"title\": \"클린코드란\", "
			+ "\"startTime\": \"2025-03-01T10:00:00\", "
			+ "\"endTime\": \"2025-03-01T12:00:00\", "
			+ "\"currentReservation\": 1, "
			+ "\"capacity\": 80, "
			+ "\"location\": \"101호\", "
			+ "\"speakerName\": \"마틴 파울러\"}]",
		requiredMode = REQUIRED)
	List<ReservationListResponse> reservation,

	@Schema(description = "즐겨 찾기 리스트",
		example = "[{"
			+ "\"wishlistId\": 1, "
			+ "\"userId\": 1, "
			+ "\"lectureId\": 1, "
			+ "\"title\": \"클린코드란\", "
			+ "\"startTime\": \"2025-03-01T10:00:00\", "
			+ "\"endTime\": \"2025-03-01T12:00:00\", "
			+ "\"currentReservation\": 1, "
			+ "\"capacity\": 80, "
			+ "\"location\": \"101호\", "
			+ "\"speakerName\": \"마틴 파울러\"}]",
		requiredMode = REQUIRED)
	List<WishlistListResponse> wishlist
) {
	public static TimetableResponse of(List<ReservationListResponse> reservation, List<WishlistListResponse> wishlist) {
		return TimetableResponse.builder()
			.reservation(reservation)
			.wishlist(wishlist)
			.build();
	}
}
