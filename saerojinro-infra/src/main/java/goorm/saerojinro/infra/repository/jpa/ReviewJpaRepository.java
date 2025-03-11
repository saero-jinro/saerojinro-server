package goorm.saerojinro.infra.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.domain.review.domain.Review;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findByLectureId(Long lectureId);

}
