package mock.repository;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeWishListRepository implements WishListRepository {
    private final List<WishList> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public List<WishList> findAllByUser(User user) {
        return data.stream()
                .filter( w -> w.getUser().getId().equals(user.getId()))
                .toList();
    }

    @Override
    public Optional<WishList> findByUserAndLectureId(User user, Long lectureId) {
        return data.stream()
                .filter( w -> w.getUser().getId().equals(user.getId()) &&
                        w.getLecture().getId().equals(lectureId))
                .findFirst();
    }

    @Override
    public boolean existsByUserAndLecture(User user, Lecture lecture) {
        return findByUserAndLectureId(user, lecture.getId()).isPresent();
    }

    @Override
    public WishList save(WishList wishList) {
        WishList newWishList = WishList.builder()
                .id(sequence.incrementAndGet())
                .user(wishList.getUser())
                .lecture(wishList.getLecture())
                .build();

        data.add(newWishList);
        return newWishList;
    }

    @Override
    public void delete(WishList wishList) { data.remove(wishList); }

    @Override
    public int countByLectureId(Long lectureId) {
        return data.stream()
            .filter(w -> w.getLecture().getId().equals(lectureId))
            .toList()
            .size();
    }

    @Override
    public List<LectureWishlistCountDto> countWishlistAllLecture() {
        Map<Long, Long> counts = data.stream()
            .collect(Collectors.groupingBy(w -> w.getLecture().getId(), Collectors.counting()));

        return counts.entrySet().stream()
            .map(entry -> new LectureWishlistCountDto(entry.getKey(), entry.getValue()))
            .toList();
    }
}
