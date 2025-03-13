package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureDetailResponse(
	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의 내용", example = "Part 1: 온오프라인 경험을 연결하는 네이버 지도가 공간지능과 만나 제공하게 될 미래 모습을 소개합니다.", requiredMode = REQUIRED)
	String contents,

	@Schema(description = "강의 자료 Uri", example = "uploads/lecture/materials/123456789.jpg")
	String materialsUri,

	@Schema(description = "강의 카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	LocalDateTime endTime,

	@Schema(description = "강의 장소", example = "온라인", requiredMode = REQUIRED)
	String location,

	@Schema(description = "강연자 이름", example = "Cole Palmer", requiredMode = REQUIRED)
	String speakerName,

	@Schema(description = "강연자 회사, 직급", example = "블라블라 스타트업 CEO", requiredMode = REQUIRED)
	String speakerPosition,

	@Schema(description = "한 줄 소개", example = "안녕하세요 OO 기업에서 OO를 담당하는 OOO 입니다.", requiredMode = REQUIRED)
	String speakerIntroduction,

	@Schema(description = "강연자 사진 Uri", example = "uploads/speaker/123456789.jpg", requiredMode = REQUIRED)
	String speakerImageUri
) {
	public static LectureDetailResponse from(Lecture lecture) {
		return LectureDetailResponse.builder()
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.materialsUri(lecture.getMaterialsUri())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.speakerName(lecture.getSpeaker().getName())
			.speakerPosition(lecture.getSpeaker().getPosition())
			.speakerIntroduction(lecture.getSpeaker().getIntroduction())
			.speakerImageUri(lecture.getSpeaker().getImageUri())
			.build();
	}
}
