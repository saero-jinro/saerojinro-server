package goorm.saerojinro.speaker.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LectureUpdateRequest(
	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull String title,

	@Schema(description = "강의 내용", example = "Part 1: 온오프라인 경험을 연결하는 네이버 지도가 공간지능과 만나 제공하게 될 미래 모습을 소개합니다.", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull String contents
	) {
}
