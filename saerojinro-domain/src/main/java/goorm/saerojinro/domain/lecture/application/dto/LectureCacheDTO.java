package goorm.saerojinro.domain.lecture.application.dto;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.Builder;

@Builder
public record LectureCacheDTO(
	Long id,

	String location,

	String title,

	Category category,

	String contents,

	Long materialsId,

	String speakerName,

	String startTime,

	String endTime,

	String speakerEmail,

	String introduction,

	String speakerPhotoUrl,

	String lectureThumbnail
) {
	public static LectureCacheDTO from(Lecture lecture) {
		Speaker speaker = lecture.getSpeaker();

		return LectureCacheDTO.builder()
			.id(lecture.getId())
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.materialsId(lecture.getMaterialFile().getId())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime().toString())
			.endTime(lecture.getEndTime().toString())
			.location(lecture.getLocation())
			.speakerName(speaker.getName())
			.speakerEmail(speaker.getEmail())
			.introduction(speaker.getIntroduction())
			.speakerPhotoUrl(speaker.getImageFile().getPhysicalPath())
			.lectureThumbnail(lecture.getThumbnailFile().getPhysicalPath())
			.build();
	}
}
