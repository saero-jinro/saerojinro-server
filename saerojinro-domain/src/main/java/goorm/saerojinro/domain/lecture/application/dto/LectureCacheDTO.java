package goorm.saerojinro.domain.lecture.application.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LectureCacheDTO(
	@Schema(description = "강의 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의 내용", example = "Part 1: 온오프라인 경험을 연결하는 네이버 지도가 공간지능과 만나 제공하게 될 미래 모습을 소개합니다.", requiredMode = REQUIRED)
	String contents,

	@Schema(description = "강의 자료 파일 ID", example = "3")
	Long materialsId,

	@Schema(description = "강의 카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	String startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	String endTime,

	@Schema(description = "강의 장소", example = "온라인", requiredMode = REQUIRED)
	String location,

	@Schema(description = "강연자 ID", example = "1L", requiredMode = REQUIRED)
	Long speakerId
) {
	public static LectureCacheDTO from(Lecture lecture) {
		return LectureCacheDTO.builder()
			.id(lecture.getId())
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.materialsId(lecture.getMaterialFile().getId())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime().toString())
			.endTime(lecture.getEndTime().toString())
			.location(lecture.getLocation())
			.speakerId(lecture.getSpeaker().getId())
			.build();
	}
}
