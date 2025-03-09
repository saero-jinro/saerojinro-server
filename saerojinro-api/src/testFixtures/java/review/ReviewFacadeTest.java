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
import goorm.saerojinro.domain.review.exception.ReviewNotAuthorizedException;
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
import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;

public class ReviewFacadeTest {
    private ReviewFacade reviewFacade;

    private FakeUserRepository userRepository;
    private FakeLectureRepository lectureRepository;
    private FakeReservationRepository reservationRepository;

    private User user;
    private User nonAdminUser;
    private Lecture lecture;

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
        reservationRepository = new FakeReservationRepository();
        ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

        FakeReviewRepository reviewRepository = new FakeReviewRepository();
        ReviewQueryService reviewQueryService = new ReviewQueryService(reviewRepository);
        ReviewCommandService reviewCommandService = new ReviewCommandService(reviewRepository, reservationQueryService);

        lectureRepository = new FakeLectureRepository();
        LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository = new FakeUserRepository();
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

        nonAdminUser = userRepository.save(
                User.builder()
                        .email("nonadminowner@example.com")
                        .password(passwordEncoder.encode("password"))
                        .name("NonAdminOwner")
                        .role(ATTENDEE)
                        .build()
        );


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

        Reservation reservationByUser = Reservation.createReservation(user, lecture);
        reservationRepository.save(reservationByUser);

        Reservation reservationByNonAdminUser = Reservation.createReservation(nonAdminUser, lecture);
        reservationRepository.save(reservationByNonAdminUser);
    }

    @Test
    @DisplayName("전체 리뷰 조회 테스트")
    public void getAllReview_Success() {
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);

        // when
        ReviewListResponse response = reviewFacade.getAllReview();

        // then
        Assertions.assertThat(response.reviews()).hasSize(1);
        Review reviewFromList = response.reviews().get(0);
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
        ReviewListResponse response = reviewFacade.getByLecture(LECTURE_ID);

        // then
        Assertions.assertThat(response.reviews()).hasSize(1);
        Assertions.assertThat(response.reviews().get(0).getLecture().getId()).isEqualTo(LECTURE_ID);
        Assertions.assertThat(response.reviews().get(0).getUser().getId()).isEqualTo(user.getId());
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
    @DisplayName("create 는 강의가 아직 끝나지 않은 경우, ReviewNotAuthorizedException 을 반환 한다.")
    public void create_Fail_LectureNotFinished() {
        // given
        Lecture upcomingLecture = Lecture.builder()
                .id(2L)
                .title("Upcoming Lecture")
                .contents("Upcoming Contents")
                .startTime(LocalDateTime.now().minusMinutes(30))
                .endTime(LocalDateTime.now().plusHours(1)) // 아직 끝나지 않음
                .location("New Location")
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();
        upcomingLecture = lectureRepository.save(upcomingLecture);

        Reservation upcomingReservation = Reservation.createReservation(user, upcomingLecture);
        reservationRepository.save(upcomingReservation);

        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);

        // then
        Lecture finalUpcomingLecture = upcomingLecture;
        Assertions.assertThatThrownBy(() -> reviewFacade.create(finalUpcomingLecture.getId(), createRequest))
                .isInstanceOf(ReviewNotAuthorizedException.class);
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
    @DisplayName("update 는 유저가 작성하지 않은 리뷰를 수정하는 요청이 발생하면, ReviewNotAuthorizedException 을 반환한다.")
    public void update_ReviewNotAuthorizedException(){
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), CONTENT, RATING);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User anotherUser = userRepository.save(
                User.builder()
                        .email("anotheruser@example.com")
                        .password(passwordEncoder.encode("anotherpassword!"))
                        .name("Another User")
                        .role(ATTENDEE)
                        .build()
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(anotherUser, anotherUser.getPassword(), anotherUser.getAuthorities())
        );

        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest("Updated content", 4.0);

        // then
        Assertions.assertThatThrownBy(() -> reviewFacade.update(reviewId, updateRequest))
                .isInstanceOf(ReviewNotAuthorizedException.class);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );
    }

    @Test
    @DisplayName("리뷰 삭제 성공 테스트: ADMIN 또는 작성자이면 삭제 가능")
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

    @Test
    @DisplayName("리뷰 삭제 성공 테스트: 작성자가 삭제 요청하는 경우 삭제 가능")
    public void delete_Success_asOwner() {
        // given

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(nonAdminUser, nonAdminUser.getPassword(), nonAdminUser.getAuthorities())
        );

        ReviewCreateRequest createRequest = new ReviewCreateRequest(nonAdminUser.getId(), "Review by non-admin owner", 4.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        // when
        reviewFacade.delete(reviewId);

        // then
        ReviewListResponse response = reviewFacade.getAllReview();
        Assertions.assertThat(response.reviews()).isEmpty();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );
    }

    @Test
    @DisplayName("리뷰 삭제 실패 테스트: ADMIN이 아니고, 작성자도 아닌 경우 삭제 불가")
    public void delete_ReviewNotAuthorizedException(){
        // given
        ReviewCreateRequest createRequest = new ReviewCreateRequest(user.getId(), "Review to delete", 4.0);
        ReviewCreateResponse createResponse = reviewFacade.create(LECTURE_ID, createRequest);
        Long reviewId = createResponse.id();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User nonOwnerUser = userRepository.save(
                User.builder()
                        .email("nonowner@example.com")
                        .password(passwordEncoder.encode("password"))
                        .name("Non Owner")
                        .role(ATTENDEE)
                        .build()
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(nonOwnerUser, nonOwnerUser.getPassword(), nonOwnerUser.getAuthorities())
        );

        // then
        Assertions.assertThatThrownBy(() -> reviewFacade.delete(reviewId))
                .isInstanceOf(ReviewNotAuthorizedException.class);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );
    }
}
