package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto;
import goorm.saerojinro.infra.repository.jpa.WishListJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepository {
    private final WishListJpaRepository wishListJpaRepository;

    @Override
    public List<WishList> findAllByUser(User user) {
        return wishListJpaRepository.findAllByUser(user);
    }

    @Override
    public Optional<WishList> findByUserAndLectureId(User user, Long lectureId) {
        return wishListJpaRepository.findByUserAndLectureId(user, lectureId);
    }

    @Override
    public boolean existsByUserAndLecture(User user, Lecture lecture) {
        return wishListJpaRepository.existsByUserAndLecture(user, lecture);
    }

    @Override
    public WishList save(WishList wishList) {
        return wishListJpaRepository.save(wishList);
    }

    @Override
    public void delete(WishList wishList) {
        wishListJpaRepository.delete(wishList);
    }

    @Override
    public int countByLectureId(Long lectureId) {
        return wishListJpaRepository.countByLectureId(lectureId);
    }

    @Override
    public List<LectureWishlistCountDto> countWishlistAllLecture() {
        return wishListJpaRepository.countWishlistAllLecture();
    }
}
