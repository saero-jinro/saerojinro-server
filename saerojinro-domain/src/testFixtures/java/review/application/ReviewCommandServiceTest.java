package review.application;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.review.application.ReviewCommandService;
import goorm.saerojinro.domain.review.application.ReviewQueryService;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.review.domain.ReviewRepository;
import goorm.saerojinro.domain.review.exception.ReviewNotAuthorizedException;
import goorm.saerojinro.domain.review.exception.ReviewNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewCommandServiceTest {
    private ReviewCommandService reviewCommandService;
    private ReviewQueryService reviewQueryService;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private static final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

    private static final String CONTENT = "리뷰 입니다";
    private static final Double RATING = 5.0;

    @BeforeEach
    void init(){
        ReservationRepository reservationRepository = new FakeReservationRepository();
        ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);
        ReviewRepository reviewRepository = new FakeReviewRepository();
        reviewCommandService = new ReviewCommandService(reviewRepository, reservationQueryService);
        reviewQueryService = new ReviewQueryService(reviewRepository);

        User user = User.builder()
                .id(USER_ID)
                .role(BaseRole.ATTENDEE)
                .build();

        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();

        Reservation reservation = Reservation.createReservation(user, lecture);
        reservationRepository.save(reservation);
    }

    private User createUser(Long id) {
        return User.builder()
                .id(id)
                .role(BaseRole.ATTENDEE)
                .build();
    }

    private Lecture createLecture(Long id) {
        return Lecture.builder()
                .id(id)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();
    }

    @Test
    @DisplayName("create 는 리뷰 데이터를 생성 및 저장 합니다.")
    public void create_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        // when
        Review createdReview = reviewCommandService.create(user, lecture, CONTENT, RATING);

        // then
        assertThat(createdReview.getUser().getId()).isEqualTo(USER_ID);
        assertThat(createdReview.getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("create 는 예약 일정이 없는 유저가 리뷰를 생성할 때, ReviewNotAuthorizedException 을 반환 합니다.")
    public void create_ReviewNotAuthorized(){
        // given
        User user = createUser(2L);
        Lecture lecture = createLecture(LECTURE_ID);

        // then
        assertThrows(ReviewNotAuthorizedException.class,
                () -> reviewCommandService.create(user, lecture, CONTENT, RATING));
    }

    @Test
    @DisplayName("update 는 리뷰 데이터를 수정합니다.")
    public void update_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);
        Review createdReview = reviewCommandService.create(user, lecture, CONTENT, RATING);

        String newContent = "수정된 리뷰 입니다.";
        Double newRating = 0.0;

        // when
        reviewCommandService.update(user, newContent, newRating, createdReview);

        // then
        Assertions.assertThat(createdReview.getContent()).isEqualTo(newContent);
        Assertions.assertThat(createdReview.getRating()).isEqualTo(newRating);
    }

    @Test
    @DisplayName("update 는 유저가 리뷰 데이터 작성자가 아니면 ReviewNotAuthorizedException을 반환 합니다.")
    public void update_ReviewNotAuthorizedException(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);
        Review createdReview = reviewCommandService.create(user, lecture, CONTENT, RATING);

        User anotherUser = createUser(2L);

        // when
        assertThrows(ReviewNotAuthorizedException.class,
                () -> reviewCommandService.update(anotherUser, CONTENT,RATING, createdReview));
    }

    @Test
    @DisplayName("delete 는 리뷰 데이터를 삭제 합니다.")
    public void delete_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);
        Review createdReview = reviewCommandService.create(user, lecture, CONTENT, RATING);

        // when
        reviewCommandService.delete(user, createdReview);

        // then
        assertThrows(ReviewNotFoundException.class,
                () -> reviewQueryService.getByReviewId(createdReview.getId()));
    }

    @Test
    @DisplayName("delete 는 유저가 리뷰 데이터 작성자가 아니면 ReviewNotAuthorizedException을 반환 합니다.")
    public void delete_ReviewNotAuthorizedException(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);
        Review createdReview = reviewCommandService.create(user, lecture, CONTENT, RATING);

        User anotherUser = createUser(2L);

        // then
        assertThrows(ReviewNotAuthorizedException.class,
                () -> reviewCommandService.delete(anotherUser, createdReview));
    }

}
