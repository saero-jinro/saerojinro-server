package goorm.saerojinro.domain.wishlist.domain;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface WishListRepository {

    List<WishList> findAllByUser(User user);

    Optional<WishList> findByUserAndLecture(User user, Lecture lecture);

    boolean existsByUserAndLecture(User user, Lecture lecture);

    WishList save(WishList wishList);

    void delete(WishList wishList);

	int countByLectureId(Long lectureId);
}
