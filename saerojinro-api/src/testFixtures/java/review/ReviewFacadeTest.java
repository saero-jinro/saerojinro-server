package review;

import goorm.saerojinro.api.review.application.ReviewFacade;
import goorm.saerojinro.api.review.presentation.request.ReviewCreateRequest;
import goorm.saerojinro.api.review.presentation.request.ReviewUpdateRequest;
import goorm.saerojinro.api.review.presentation.response.ReviewCreateResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewDeleteResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewListResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewUpdateResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.review.application.ReviewCommandService;
import goorm.saerojinro.domain.review.application.ReviewQueryService;
import goorm.saerojinro.domain.review.domain.Review;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeReviewRepository;
import mock.repository.FakeUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;

public class ReviewFacadeTest {
    private ReviewFacade reviewFacade;
    private User user;
    private Lecture lecture;

    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private static final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

    @BeforeEach
    void init() {
        FakeReservationRepository reservationRepository = new FakeReservationRepository();
        ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

        FakeReviewRepository reviewRepository = new FakeReviewRepository();
        ReviewQueryService reviewQueryService = new ReviewQueryService(reviewRepository);
        ReviewCommandService reviewCommandService = new ReviewCommandService(reviewRepository, reservationQueryService);

        FakeLectureRepository lectureRepository = new FakeLectureRepository();
        LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        FakeUserRepository userRepository = new FakeUserRepository();
        UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

        reviewFacade = new ReviewFacade(reviewQueryService, reviewCommandService, lectureQueryService, userQueryService);

        user = userRepository.save(
                User.builder()
                        .email("email@email.com")
                        .password(passwordEncoder.encode("password1234!"))
                        .name("박민준")
                        .role(ADMIN)
                        .build()
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );

        // 테스트용 강의 생성: 강의 시작 및 종료 시간이 현재 시간 기준 과거로 설정되어, 강의 종료 검증을 통과함.
        lecture = lectureRepository.save(
                Lecture.builder()
                        .id(LECTURE_ID)
                        .title(LECTURE_TITLE)
                        .contents(LECTURE_CONTENTS)
                        .startTime(LocalDateTime.now().minusHours(2))
                        .endTime(LocalDateTime.now().minusHours(1))
                        .location(LOCATION)
                        .category(CATEGORY)
                        .lectureStatus(STATUS)
                        .build()
        );

        Reservation reservation = Reservation.createReservation(user, lecture);
        reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("전체 리뷰 조회 테스트")
    public void testGetAllReview() {
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Great lecture!", 5.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);

        ReviewListResponse listResponse = reviewFacade.getAllReview();

        Assertions.assertThat(listResponse.reviews()).hasSize(1);
        Review reviewFromList = listResponse.reviews().get(0);
        Assertions.assertThat(reviewFromList.getId()).isEqualTo(createResponse.id());
        Assertions.assertThat(reviewFromList.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("강의별 리뷰 조회 테스트")
    public void testGetByLecture() {
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Informative lecture", 4.5);
        reviewFacade.create(LECTURE_ID, createRequest);

        ReviewListResponse listResponse = reviewFacade.getByLecture(LECTURE_ID);

        Assertions.assertThat(listResponse.reviews()).hasSize(1);
        Assertions.assertThat(listResponse.reviews().get(0).getLecture().getId()).isEqualTo(LECTURE_ID);
        Assertions.assertThat(listResponse.reviews().get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    public void testCreateReview() {
        String content = "Excellent lecture!";
        double rating = 5.0;
        ReviewCreateRequest request = new ReviewCreateRequest(user.getId(), content, rating);

        ReviewCreateResponse response = reviewFacade.create(LECTURE_ID, request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.content()).isEqualTo(content);
        Assertions.assertThat(response.rating()).isEqualTo(rating);
        Assertions.assertThat(response.lectureId()).isEqualTo(LECTURE_ID);
        Assertions.assertThat(response.userId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    public void testUpdateReview() {
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Original content", 3.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest("Updated content", 4.0);
        ReviewUpdateResponse updateResponse = reviewFacade.update(reviewId, updateRequest);

        Assertions.assertThat(updateResponse).isNotNull();
        Assertions.assertThat(updateResponse.content()).isEqualTo("Updated content");
        Assertions.assertThat(updateResponse.rating()).isEqualTo(4.0);
        Assertions.assertThat(updateResponse.userId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    public void testDeleteReview() {
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Review to delete", 4.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        ReviewDeleteResponse deleteResponse = reviewFacade.delete(reviewId);

        Assertions.assertThat(deleteResponse).isNotNull();
        Assertions.assertThat(deleteResponse.id()).isEqualTo(reviewId);

        ReviewListResponse listResponse = reviewFacade.getAllReview();
        Assertions.assertThat(listResponse.reviews()).isEmpty();
    }
}
