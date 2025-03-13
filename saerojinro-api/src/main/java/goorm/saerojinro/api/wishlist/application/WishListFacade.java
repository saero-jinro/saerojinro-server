package goorm.saerojinro.api.wishlist.application;

import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_VIEW;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_WISHLIST;

import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListCommandService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WishListFacade {
	private final WishListQueryService wishListQueryService;
	private final WishListCommandService wishListCommandService;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;
	private final LogEventProducer logEventProducer;

	@Transactional(readOnly = true)
	public WishListResponse getAllWishList() {
		User user = userQueryService.me();
		List<WishList> wishLists = wishListQueryService.getAllByUser(user);
		return WishListResponse.from(wishLists);
	}

	@Transactional(readOnly = true)
	public WishListResponse getByUserAndStartTime(LocalDateTime startTime){
		User user = userQueryService.me();
		List<WishList> wishLists = wishListQueryService.getByUserAndStartTime(user, startTime);
		return WishListResponse.from(wishLists);
	}

	@Transactional
	public WishListCreateResponse create(Long lectureId) {
		User user = userQueryService.me();
		Lecture lecture = lectureQueryService.getById(lectureId);
		WishList wishList = wishListCommandService.create(user, lecture);

		LogEventDto logEventDto = LogEventDto.of(user.getId(), lecture.getId(), LECTURE_WISHLIST, lecture.getCategory());
		logEventProducer.sendMessage(logEventDto);

		return WishListCreateResponse.from(wishList);
	}

	@Transactional
	public void delete(Long lectureId) {
		User user = userQueryService.me();

		WishList wishList = wishListQueryService.getByUserAndLectureId(user, lectureId);
		wishListCommandService.delete(wishList);
	}

}
