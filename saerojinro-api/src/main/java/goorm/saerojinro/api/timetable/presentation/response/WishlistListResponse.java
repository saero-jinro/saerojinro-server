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
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.currentReservation(currentReservation)
			.capacity(Math.toIntExact(lecture.getMaxCapacity()))
			.location(lecture.getLocation())
			.speakerName(lecture.getSpeaker().getName())
			.build();
	}
}
