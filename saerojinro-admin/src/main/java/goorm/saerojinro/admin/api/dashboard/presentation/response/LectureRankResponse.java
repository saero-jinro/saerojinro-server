package goorm.saerojinro.admin.api.dashboard.presentation.response;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureRankResponse(
	@Schema(description = "강의 ID", example = "1", requiredMode = REQUIRED)
	Long lectureId,

	@Schema(description = "순위", example = "1", requiredMode = REQUIRED)
	int rank,

	@Schema(description = "제목", example = "그렇게 하는거 아닌데", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강연자 이름", example = "마틴 파울러", requiredMode = REQUIRED)
	String speaker,

	@Schema(description = "현재 예약 인원", example = "50", requiredMode = REQUIRED)
	int reservation,

	@Schema(description = "현재 즐겨찾기 인원", example = "20", requiredMode = REQUIRED)
	int wishlist
) {
	public static LectureRankResponse from(Dashboard dashboard, int rank) {
		return LectureRankResponse.builder()
			.lectureId(dashboard.getId())
			.rank(rank)
			.title(dashboard.getTitle())
			.speaker(dashboard.getSpeaker())
			.reservation(dashboard.getReservation())
			.wishlist(dashboard.getWishlist())
			.build();
	}
}
