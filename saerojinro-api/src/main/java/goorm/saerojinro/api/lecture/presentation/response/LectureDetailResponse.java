package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.dto.LectureCacheDTO;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureDetailResponse(
	@Schema(description = "강의 장소", example = "온라인", requiredMode = REQUIRED)
	String location,

	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의 카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category,

	@Schema(description = "강의 내용", example = "이 강의는 ~ 만들 수 있습니다", requiredMode = REQUIRED)
	String contents,

	@Schema(description = "강의 자료 파일 ID", example = "3", requiredMode = REQUIRED)
	Long materialsId,

	@Schema(description = "강연자 이름", example = "박민수", requiredMode = REQUIRED)
	String speakerName,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	String startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	String endTime,

	@Schema(description = "강연자 이메일", example = "google@google.com", requiredMode = REQUIRED)
	String speakerEmail,

	@Schema(description = "강연자 소개", example = "안녕하세요 박민수입니다", requiredMode = REQUIRED)
	String introduction,

	@Schema(description = "강연자 사진 url", example = "bucketUrl/uploads/{lectureId}/speaker", requiredMode = REQUIRED)
	String speakerPhotoUrl
) {
	public static LectureDetailResponse from(LectureCacheDTO lectureCacheDTO) {
		String s3Prefix = "https://saerojinro-bucket.s3.ap-northeast-2.amazonaws.com/";
		String speakerPhotoUrl = s3Prefix + lectureCacheDTO.speakerPhotoUrl();

		return LectureDetailResponse.builder()
			.title(lectureCacheDTO.title())
			.contents(lectureCacheDTO.contents())
			.materialsId(lectureCacheDTO.materialsId())
			.category(lectureCacheDTO.category())
			.startTime(lectureCacheDTO.startTime())
			.endTime(lectureCacheDTO.endTime())
			.location(lectureCacheDTO.location())
      		.speakerName(lectureCacheDTO.speakerName())
			.speakerEmail(lectureCacheDTO.speakerEmail())
			.introduction(lectureCacheDTO.introduction())
			.speakerPhotoUrl(speakerPhotoUrl)
			.build();
	}
}
