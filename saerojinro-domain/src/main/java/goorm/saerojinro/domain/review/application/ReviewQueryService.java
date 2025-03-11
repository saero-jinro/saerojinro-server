package goorm.saerojinro.domain.review.application;

import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;
import goorm.saerojinro.domain.review.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public List<Review> getByLectureId(Long lectureId){
        return reviewRepository.findByLectureId(lectureId);
    }

    public Review getByReviewId(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
    }

}
