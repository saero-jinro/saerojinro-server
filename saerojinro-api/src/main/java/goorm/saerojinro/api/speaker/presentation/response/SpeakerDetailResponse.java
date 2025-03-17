package goorm.saerojinro.api.speaker.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record SpeakerDetailResponse(
	@Schema(description = "강연자 사진 url", example = "bucketUrl/uploads/{lectureId}/speaker", requiredMode = REQUIRED)
	@NotNull String speakerPhotoUrl,

	@Schema(description = "강연자 이름", example = "김지훈", requiredMode = REQUIRED)
	@NotNull String name,

	@Schema(description = "강연자 이메일", example = "jihoon.kim@example.com", requiredMode = REQUIRED)
	@NotNull String email,

	@Schema(description = "강연자 회사, 직급", example = "3년차 A 테크놀로지 프론트엔드 엔지니어", requiredMode = REQUIRED)
	@NotNull String position,

	@Schema(description = "한 줄 소개", example = "김지훈 강사는 ~ 공유하고 있습니다.", requiredMode = REQUIRED)
	@NotNull String introduction,

	@Schema(description = "주요 경력", example = "글로벌 ID 기업 웹 플랫폼 개발(5년)", requiredMode = REQUIRED)
	@NotNull String filmography,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	LocalDateTime endTime,

	@Schema(description = "강의명", example = "AI와 미래 업무: 생성형 AI가 바꾸는 기업 혁신", requiredMode = REQUIRED)
	String title,

	@Schema(description = "카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category

) {
	public static SpeakerDetailResponse from(Speaker speaker, Lecture lecture) {
		return SpeakerDetailResponse.builder()
			.speakerPhotoUrl(speaker.getImageFile().getPhysicalPath())
			.name(speaker.getName())
			.email(speaker.getEmail())
			.position(speaker.getPosition())
			.introduction(speaker.getIntroduction())
			.filmography(speaker.getFilmography())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.title(lecture.getTitle())
			.category(lecture.getCategory())
			.build();
	}
}
