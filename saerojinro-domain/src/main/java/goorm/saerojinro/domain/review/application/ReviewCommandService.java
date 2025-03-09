package goorm.saerojinro.domain.review.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final ReservationQueryService reservationQueryService;

    public Review create(User user, Lecture lecture, String content, Double rating){

        Review review = Review.create(user, lecture, content, rating);
        return reviewRepository.save(review);
    }

    public void update(String content, Double rating, Review review){

        review.update(content, rating);
    }

    public void delete(User user, Review review){

        reviewRepository.delete(review);
    }
}
