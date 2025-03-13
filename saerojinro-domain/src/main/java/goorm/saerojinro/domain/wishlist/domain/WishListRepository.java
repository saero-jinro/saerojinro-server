package goorm.saerojinro.domain.wishlist.domain;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto;

import java.util.List;
import java.util.Optional;

public interface WishListRepository {
    List<WishList> findAllByUser(User user);

    Optional<WishList> findByUserAndLectureId(User user, Long lectureId);

    boolean existsByUserAndLecture(User user, Lecture lecture);

    WishList save(WishList wishList);

    void delete(WishList wishList);

	int countByLectureId(Long lectureId);

    List<LectureWishlistCountDto> countWishlistAllLecture();
}
