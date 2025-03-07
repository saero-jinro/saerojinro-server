package goorm.saerojinro.api.review.presentation;

import goorm.saerojinro.api.review.application.ReviewFacade;
import goorm.saerojinro.api.review.presentation.request.ReviewCreateRequest;
import goorm.saerojinro.api.review.presentation.request.ReviewUpdateRequest;
import goorm.saerojinro.api.review.presentation.response.ReviewCreateResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewDeleteResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewListResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewControllerImpl implements ReviewController{
    private final ReviewFacade reviewFacade;

    @Override
    @GetMapping("/admin/reviews")
    public ResponseEntity<ReviewListResponse> getAllReview(){
        ReviewListResponse response = reviewFacade.getAllReview();
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @GetMapping("/lectures/{lectureId}/reviews")
    public ResponseEntity<ReviewListResponse> getByLecture(@PathVariable("lectureId") Long lectureId) {
        ReviewListResponse response = reviewFacade.getByLecture(lectureId);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @PostMapping("/lectures/{lectureId}/reviews/create")
    public ResponseEntity<ReviewCreateResponse> create(@PathVariable("lectureId") Long lectureId, ReviewCreateRequest request) {
        ReviewCreateResponse response = reviewFacade.create(lectureId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @PatchMapping("/lectures/{lectureId}/reviews/{reviewId}")
    public ResponseEntity<ReviewUpdateResponse> update(@PathVariable("reviewId") Long reviewId, ReviewUpdateRequest request) {
        ReviewUpdateResponse response = reviewFacade.update(reviewId, request);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @DeleteMapping("/lectures/{lectureId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> delete(@PathVariable("reviewId") Long reviewId) {
        ReviewDeleteResponse response = reviewFacade.delete(reviewId);
        return ResponseEntity.status(OK).body(response);
    }
}
