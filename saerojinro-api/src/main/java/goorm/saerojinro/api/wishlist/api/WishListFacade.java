package goorm.saerojinro.api.wishlist.api;

import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListDeleteResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListCommandService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WishListFacade {
	private final WishListQueryService wishListQueryService;
	private final WishListCommandService wishListCommandService;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;

	public WishListResponse getAllWishList(Long userId) {
		User user = getUser(userId);

		List<WishList> wishLists = wishListQueryService.getAllByUser(user);
		return WishListResponse.from(wishLists);
	}

	public WishListCreateResponse create(Long userId, Long lectureId) {
		User user = getUser(userId);
		Lecture lecture = getLecture(lectureId);

		WishList wishList = wishListCommandService.create(user, lecture);
		return WishListCreateResponse.from(wishList);
	}

	public WishListDeleteResponse delete(Long userId, Long lectureId) {
		User user = getUser(userId);
		Lecture lecture = getLecture(lectureId);

		WishList wishList = wishListQueryService.getByUserAndLecture(user, lecture);
		wishListCommandService.delete(wishList);
		return WishListDeleteResponse.from(wishList);
	}

	private User getUser(Long userId) {
		return userQueryService.getById(userId);
	}

	private Lecture getLecture(Long lectureId) {
		return lectureQueryService.getByLectureId(lectureId);
	}
}
