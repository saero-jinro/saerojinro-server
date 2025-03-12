package goorm.saerojinro.domain.wishlist.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.exception.WishListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListQueryService {
    private final WishListRepository wishListRepository;

    public List<WishList> getAllByUser(User user){
        return wishListRepository.findAllByUser(user);
    }

    public WishList getByUserAndLectureId(User user, Long lectureId){
        return wishListRepository.findByUserAndLectureId(user, lectureId)
                .orElseThrow(WishListNotFoundException::new);
    }

    public boolean existCheck(User user, Lecture lecture){
        return wishListRepository.existsByUserAndLecture(user, lecture);
    }
}
