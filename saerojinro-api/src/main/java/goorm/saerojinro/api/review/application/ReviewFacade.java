package goorm.saerojinro.api.review.application;

import goorm.saerojinro.api.review.presentation.request.ReviewCreateRequest;
import goorm.saerojinro.api.review.presentation.request.ReviewUpdateRequest;
import goorm.saerojinro.api.review.presentation.response.ReviewCreateResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.review.application.ReviewCommandService;
import goorm.saerojinro.domain.review.application.ReviewQueryService;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.exception.ReviewNotAuthorizedException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;

@Component
@RequiredArgsConstructor
public class ReviewFacade {
    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;
    private final LectureQueryService lectureQueryService;
    private final UserQueryService userQueryService;
    private final ReservationQueryService reservationQueryService;

    @Transactional(readOnly = true)
    public ReviewListResponse getAllReview(){
        List<Review> findReview = reviewQueryService.getAll();
        return ReviewListResponse.from(findReview);
    }

    @Transactional(readOnly = true)
    public ReviewListResponse getByLecture(Long lectureId){
        Lecture lecture = lectureQueryService.getByLectureId(lectureId);

        List<Review> findReview = reviewQueryService.getByLecture(lecture);
        return ReviewListResponse.from(findReview);
    }

    @Transactional
    public ReviewCreateResponse create(Long lectureId, ReviewCreateRequest request){
        User user = userQueryService.getById(request.userId());
        Lecture lecture = lectureQueryService.getByLectureId(lectureId);

        if (!reservationQueryService.existsCheck(user, lecture) || LocalDateTime.now().isBefore(lecture.getEndTime())) {
            throw new ReviewNotAuthorizedException();
        }

        Review review = reviewCommandService.create(
                user, lecture, request.content(), request.rating());
        return ReviewCreateResponse.from(review);
    }

    @Transactional
    public void update(Long reviewId, ReviewUpdateRequest request){
        User user = userQueryService.me();

        Review review = reviewQueryService.getByReviewId(reviewId);

        if(!user.getId().equals(review.getUser().getId())){
            throw new ReviewNotAuthorizedException();
        }

        reviewCommandService.update(request.content(), request.rating(), review);
    }

    @Transactional
    public void delete(Long reviewId){
        User user = userQueryService.me();

        Review review = reviewQueryService.getByReviewId(reviewId);

        if(!review.getUser().getId().equals(user.getId()) && !user.getRole().equals(ADMIN)){
          throw new ReviewNotAuthorizedException();
        }

        reviewCommandService.delete(user, review);
    }
}
