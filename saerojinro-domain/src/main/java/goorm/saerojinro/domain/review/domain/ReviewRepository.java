package goorm.saerojinro.domain.review.domain;

import goorm.saerojinro.domain.lecture.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    List<Review> findAll();

    List<Review> findByLecture(Lecture lecture);

    Optional<Review> findById(Long reviewId);

    Review save(Review review);

    void delete(Review review);
}
