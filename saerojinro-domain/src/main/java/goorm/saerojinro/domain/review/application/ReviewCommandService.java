package goorm.saerojinro.domain.review.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;
import goorm.saerojinro.domain.review.exception.ReviewNotAuthorizedException;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.*;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final ReservationQueryService reservationQueryService;

    public Review create(User user, Lecture lecture, String content, Double rating){
        if(!reservationQueryService.existsCheck(user,lecture) ||
                LocalDateTime.now().isBefore(lecture.getEndTime())){
            throw new ReviewNotAuthorizedException();
        }

        Review review = Review.createReview(user, lecture, content, rating);
        return reviewRepository.save(review);
    }

    public void update(User user, String content, Double rating, Review review){
        if(!review.getUser().getId().equals(user.getId())){
            throw new ReviewNotAuthorizedException();
        }

        review.update(content, rating);
    }

    public void delete(User user, Review review){
        if(!review.getUser().getId().equals(user.getId()) && !user.getRole().equals(ADMIN)){
            throw new ReviewNotAuthorizedException();
        }

        reviewRepository.delete(review);
    }
}
