package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;
import goorm.saerojinro.infra.repository.jpa.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public List<Review> findAll() {
        return reviewJpaRepository.findAll();
    }

    @Override
    public List<Review> findByLecture(Lecture lecture) {
        return reviewJpaRepository.findByLecture(lecture);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {
        return reviewJpaRepository.findById(reviewId);
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewJpaRepository.delete(review);
    }
}
