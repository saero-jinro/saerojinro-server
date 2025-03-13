package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishListJpaRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByUser(User user);

    Optional<WishList> findByUserAndLectureId(User user, Long lectureId);

    boolean existsByUserAndLecture(User user, Lecture lecture);

    int countByLectureId(Long lectureId);

	@Query("SELECT new goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto(w.lecture.id, COUNT(w)) " +
		"FROM WishList w GROUP BY w.lecture.id")
	List<LectureWishlistCountDto> countWishlistAllLecture();
}
