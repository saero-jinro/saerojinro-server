package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListJpaRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByUser(User user);

    Optional<WishList> findByUserAndLecture(User user, Lecture lecture);

    boolean existByUserAndLecture(User user, Lecture lecture);
}
