package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishlistListResponse(
	Long wishlistId,
	Long userId,
	Long lectureId,
	String title,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int currentReservation,
	int capacity,
	String location,
	String speakerName
) {
	public static WishlistListResponse from(WishList wishList, int currentReservation) {
		Lecture lecture = wishList.getLecture();

		return WishlistListResponse.builder()
			.wishlistId(wishList.getId())
			.userId(wishList.getUser().getId())
			.lectureId(lecture.getId())
			.title(lecture.getTitle())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.currentReservation(currentReservation)
			.capacity((int) (lecture.getMaxCapacity() * 0.8))
			.location(lecture.getLocation())
			.speakerName(lecture.getSpeaker().getName())
			.build();
	}
}
