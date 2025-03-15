package goorm.saerojinro.api.speaker.presentation.response;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record SpeakerDetailResponse(
	@Schema(description = "강연자 사진", example = "1", requiredMode = REQUIRED)
	@NotNull Long speakerPhotoId,

	@Schema(description = "강연자 이름", example = "Cole Palmer", requiredMode = REQUIRED)
	@NotNull String name,

	@Schema(description = "강연자 이메일", example = "google@gmail.com", requiredMode = REQUIRED)
	@NotNull String email,

	@Schema(description = "한 줄 소개", example = "안녕하세요 OO 기업에서 OO를 담당하는 OOO 입니다.", requiredMode = REQUIRED)
	@NotNull String introduction,

	@Schema(description = "강연자 회사, 직급", example = "블라블라 스타트업 CEO", requiredMode = REQUIRED)
	@NotNull String position,

	@Schema(description = "주요 경력", example = "OO 회사 / 프로젝트 OO 담당", requiredMode = REQUIRED)
	@NotNull String filmography

) {
	public static SpeakerDetailResponse from(Speaker speaker) {
		return SpeakerDetailResponse.builder()
			.speakerPhotoId(speaker.getImageFile().getId())
			.name(speaker.getName())
			.email(speaker.getEmail())
			.introduction(speaker.getIntroduction())
			.position(speaker.getPosition())
			.filmography(speaker.getFilmography())
			.build();
	}
}
