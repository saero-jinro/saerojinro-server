package goorm.saerojinro.admin.api.lecture.presentation.request;

import goorm.saerojinro.common.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Builder
public record 	LectureCreateRequest(
	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	@NotNull String title,

	@Schema(description = "강의 내용", example = "Part 1: 온오프라인 경험을 연결하는 네이버 지도가 공간지능과 만나 제공하게 될 미래 모습을 소개합니다.", requiredMode = REQUIRED)
	@NotNull String contents,

	@Schema(description = "강의 수용 인원", example = "100", requiredMode = REQUIRED)
	@NotNull @Positive Long maxCapacity,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	@NotNull LocalDateTime startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	@NotNull LocalDateTime endTime,

	@Schema(description = "강의 장소", example = "온라인", requiredMode = REQUIRED)
	@NotNull String location,

	@Schema(description = "강의 카테고리", example = "BACKEND", requiredMode = REQUIRED)
	@NotNull Category category,

	@Schema(description = "강연자 이름", example = "Cole Palmer", requiredMode = REQUIRED)
	@NotNull String speakerName,

	@Schema(description = "강연자 이메일", example = "google@gmail.com", requiredMode = REQUIRED)
	@NotNull String speakerEmail,

	@Schema(description = "강연자 회사, 직급", example = "블라블라 스타트업 CEO", requiredMode = REQUIRED)
	@NotNull String speakerPosition,

	@Schema(description = "한 줄 소개", example = "안녕하세요 OO 기업에서 OO를 담당하는 OOO 입니다.", requiredMode = REQUIRED)
	@NotNull String speakerIntroduction,

	@Schema(description = "상세 약력", example = "OO 회사 / 프로젝트 OO 담당", requiredMode = REQUIRED)
	@NotNull String speakerFilmography,

	@Schema(description = "강연자 사진", example = "https://example.com/speaker_photo.jpg", requiredMode = REQUIRED)
	@NotNull String speakerPhotoUri
) {}
