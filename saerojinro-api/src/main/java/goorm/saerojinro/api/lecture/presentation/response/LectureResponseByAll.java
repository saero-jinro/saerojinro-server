package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Builder
public record LectureResponseByAll(
	@Schema(description = "카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category,

	@Schema(description = "강의 썸네일 Uri", example = "uploads/lecture/thumbnail/123456789.jpg", requiredMode = REQUIRED)
	String thumbnailUri,

	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의 내용", example = "Part 1: 온오프라인 경험을 연결하는 네이버 지도가 공간지능과 만나 제공하게 될 미래 모습을 소개합니다.", requiredMode = REQUIRED)
	String contents,

	@Schema(description = "강연자 이름", example = "Cole Palmer", requiredMode = REQUIRED)
	String speakerName,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	LocalDateTime endTime,

	@Schema(description = "강의 장소", example = "온라인", requiredMode = REQUIRED)
	String location
) {
	public static LectureResponseByAll from(Lecture lecture) {
		return LectureResponseByAll.builder()
			.category(lecture.getCategory())
			.thumbnailUri(lecture.getThumbnailUri())
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.speakerName(lecture.getSpeaker().getName())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.build();
	}
}
