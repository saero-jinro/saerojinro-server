package review;

import goorm.saerojinro.api.review.application.ReviewFacade;
import goorm.saerojinro.api.review.presentation.request.ReviewCreateRequest;
import goorm.saerojinro.api.review.presentation.request.ReviewUpdateRequest;
import goorm.saerojinro.api.review.presentation.response.ReviewCreateResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewListResponse;
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

    private final String CONTENT = "Excellent lecture!";
    private final Double RATING = 5.0;

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

        reviewFacade = new ReviewFacade(reviewQueryService, reviewCommandService, lectureQueryService, userQueryService, reservationQueryService);

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

        Lecture lecture = lectureRepository.save(
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
    public void getAllReview_Success() {
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);

        // when
        ReviewListResponse Response = reviewFacade.getAllReview();

        // then
        Assertions.assertThat(Response.reviews()).hasSize(1);
        Review reviewFromList = Response.reviews().get(0);
        Assertions.assertThat(reviewFromList.getId()).isEqualTo(createResponse.id());
        Assertions.assertThat(reviewFromList.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("강의별 리뷰 조회 테스트")
    public void getByLecture_Success() {
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);
        reviewFacade.create(LECTURE_ID, createRequest);

        // when
        ReviewListResponse Response = reviewFacade.getByLecture(LECTURE_ID);

        // then
        Assertions.assertThat(Response.reviews()).hasSize(1);
        Assertions.assertThat(Response.reviews().get(0).getLecture().getId()).isEqualTo(LECTURE_ID);
        Assertions.assertThat(Response.reviews().get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    public void create_Success() {
        // given
        ReviewCreateRequest request = new ReviewCreateRequest(user.getId(), CONTENT, RATING);

        // when
        ReviewCreateResponse response = reviewFacade.create(LECTURE_ID, request);

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.content()).isEqualTo(CONTENT);
        Assertions.assertThat(response.rating()).isEqualTo(RATING);
        Assertions.assertThat(response.lectureId()).isEqualTo(LECTURE_ID);
        Assertions.assertThat(response.userId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    public void update_Success(){
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest("Updated content", 4.0);

        // when
        reviewFacade.update(reviewId, updateRequest);

        // then
        ReviewListResponse listResponse = reviewFacade.getAllReview();
        Assertions.assertThat(listResponse.reviews()).hasSize(1);

        Review updatedReview = listResponse.reviews().get(0);
        Assertions.assertThat(updatedReview.getContent()).isEqualTo("Updated content");
        Assertions.assertThat(updatedReview.getRating()).isEqualTo(4.0);
        Assertions.assertThat(updatedReview.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    public void delete_Success() {
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Review to delete", 4.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        // when
        reviewFacade.delete(reviewId);

        // then
        ReviewListResponse response = reviewFacade.getAllReview();
        Assertions.assertThat(response.reviews()).isEmpty();
    }
}
