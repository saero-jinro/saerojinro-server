package mock.repository;

import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReviewRepository implements ReviewRepository {
    private final List<Review> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Review> findAll() {
        return data.stream()
                .toList();
    }

    @Override
    public List<Review> findByLectureId(Long id) {
        return data.stream()
                .filter(r -> r.getLecture().getId().equals(id))
                .toList();
    }

    @Override
    public Optional<Review> findById(Long reviewId) {
        return data.stream()
                .filter(r-> r.getId().equals(reviewId))
                .findFirst();
    }

    @Override
    public Review save(Review review) {
        Review newReview = Review.builder()
                .id(sequence.getAndIncrement())
                .lecture(review.getLecture())
                .user(review.getUser())
                .content(review.getContent())
                .rating(review.getRating())
                .build();

        data.add(newReview);
        return newReview;

    }

    @Override
    public void delete(Review review) {
        data.remove(review);
    }
}
