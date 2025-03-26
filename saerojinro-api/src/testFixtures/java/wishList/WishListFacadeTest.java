package wishList;

import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import goorm.saerojinro.api.wishlist.application.*;
import goorm.saerojinro.api.wishlist.presentation.response.*;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListCommandService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import mock.producer.FakeLogEventProducer;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeLogEventRepository;
import mock.repository.FakeUserRepository;
import mock.repository.FakeWishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

public class WishListFacadeTest {
    private WishListFacade wishListFacade;

    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private User user;

    @BeforeEach
    void init(){
        FakeWishListRepository wishListRepository = new FakeWishListRepository();
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeLectureRepository lectureRepository = new FakeLectureRepository();
        WishListQueryService wishListQueryService = new WishListQueryService(wishListRepository);
        FakeLogEventProducer fakeEventLogProducer = new FakeLogEventProducer();

        UserQueryService userQueryService = new UserQueryService(userRepository, new BCryptPasswordEncoder());
        LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);
        LogEventService logEventService = new LogEventService(new FakeLogEventRepository(), userQueryService, lectureQueryService, fakeEventLogProducer);
        wishListFacade = new WishListFacade(
            wishListQueryService,
            new WishListCommandService(wishListRepository, wishListQueryService),
            userQueryService,
            lectureQueryService,
            logEventService
        );

        user = userRepository.save(User.builder()
            .email("email@email.com")
            .password("password1234!")
            .name("박민준")
            .role(ADMIN)
            .build()
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
            new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );

        Speaker speaker = Speaker.builder()
            .name("박민준")
            .build();

        File file = File.builder()
            .physicalPath("path")
            .build();

        Lecture lecture = Lecture.builder()
            .id(LECTURE_ID)
            .title(LECTURE_TITLE)
            .contents(LECTURE_CONTENTS)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .location(LOCATION)
            .category(CATEGORY)
            .speaker(speaker)
            .thumbnailFile(file)
            .build();

        lectureRepository.save(lecture);
    }

    @Test
    @DisplayName("getAllWishList 는 모든 유저 아이디에 해당 하는 모든 즐겨찾기 정보를 조회한다. ")
    public void getAllWishList_Success(){
        // given
        wishListFacade.create(LECTURE_ID);

        // when
        WishListResponse response = wishListFacade.getAllWishList();

        // then
        assertNotNull(response);
        assertEquals(1, response.response().size());
        assertEquals("박민준", response.response().get(0).speaker());
    }

    @Test
    @DisplayName("getByUseAndStartTime 은 유저 ID 와 시작 시간이 동일한 즐겨찾기 정보를 조회한다.")
    public void getByUserAndStartTime(){
        // given
        wishListFacade.create(LECTURE_ID);

        // when
        LectureSummaryListResponse response = wishListFacade.getByUserAndStartTime(START_TIME);

        // then
        assertNotNull(response);
        assertEquals(1, response.total());
        assertEquals(LECTURE_TITLE, response.responses().get(0).title());
    }

    @Test
    @DisplayName("create 는 해당하는 아이디와 강의에 대한 새로운 즐겨찾기 정보를 생성한다.")
    public void create_Success(){
        // when
        WishListCreateResponse Response = wishListFacade.create(LECTURE_ID);

        // then
        assertNotNull(Response);
        assertTrue(Response.id() > 0);
    }

    @Test
    @DisplayName("delete 는 해당하는 아이디와 강의에 대한 즐겨찾기 정보를 삭제한다.")
    public void delete_Success(){
        // given
        wishListFacade.create(LECTURE_ID);

        // when
        wishListFacade.delete(LECTURE_ID);

        // then
        assertTrue(wishListFacade.getAllWishList().response().isEmpty());
    }
}
